package br.com.cucha.myinsta;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import br.com.cucha.myinsta.image.ImageInteractorImpl;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoListFragment extends Fragment implements MainView {

    static String[] permissions = new String[] {
        Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE };;

    private static final int REQUEST_CODE_CAMERA = 1002;
    private static final int REQUEST_CODE_PERMISSIONS = 1001;

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    public static final String TAG = PhotoListFragment.class.getName();
    private Unbinder unbinder;
    private MainPresenter presenter;
    private AlertDialog unlockDialog;
    private Uri uri;
    private ImageView image;
    private AlertDialog disconnectDialog;
    private AlertDialog noSpaceDialog;
    private AlertDialog errorDialog;

    public PhotoListFragment() {
        // Required empty public constructor
    }

    public static PhotoListFragment newInstance() {

        Bundle args = new Bundle();

        PhotoListFragment fragment = new PhotoListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new MainPresenterImpl(this, new ImageInteractorImpl(getContext()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photo_list, container, false);

        image = (ImageView) view.findViewById(R.id.image);

        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter.init();
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @OnClick(R.id.fab)
    public void onFabClick(View view) {
        presenter.cameraClick();
    }

    @Override
    public void showEmptyList() {

    }

    @Override
    public boolean checkPermission() {
        for (String p : permissions) {
            int result = ActivityCompat.checkSelfPermission(getContext(), p);

            if(result == PackageManager.PERMISSION_DENIED) return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_CODE_PERMISSIONS ) {
            if(!allPermissionsGranted(grantResults)) {
                presenter.permissionDenied();
            }
        }
    }

    private boolean allPermissionsGranted(int[] grantResults) {
        for (int result : grantResults) {
            if(result == PackageManager.PERMISSION_DENIED)
                return false;
        }

        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
            Picasso.with(getContext()).load(uri).into(image);
            presenter.saveImage(uri);
        }
    }

    @Override
    public void showPermissionDialog() {
        requestPermissions(permissions, REQUEST_CODE_PERMISSIONS);
    }

    @Override
    public File getEnvFilePath() {
        File file = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return file;
    }

    @Override
    public boolean isStorageMounted() {
        String externalStorageState = Environment.getExternalStorageState();

        return externalStorageState.equals(Environment.MEDIA_MOUNTED);
    }

    @Override
    public void startCamera(File file) {

        uri = FileProvider.getUriForFile(getContext(), GENERAL.FILE_AUTHORITY, file);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    @Override
    public void onActivityResult(Uri uri) {

    }

    @Override
    public boolean shouldShowDialog() {

        for(String p : permissions) {
            boolean b = ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), p);

            if(!b) return false;
        }

        return true;
    }

    @Override
    public void showUnlockPermissionsDialog() {
        if(unlockDialog == null) {
            unlockDialog = new AlertDialog.Builder(getContext())
                    .setTitle(getString(R.string.without_permissions))
                    .setMessage(getString(R.string.it_looks_you_locked_permissions_))
                    .create();
        }

        unlockDialog.show();
    }

    @Override
    public String[] getPermissions() {
        return permissions;
    }

    @Override
    public void showDisconnectFromPCDialog() {

        if(disconnectDialog == null) {
            disconnectDialog = new AlertDialog.Builder(getContext())
                    .setTitle(getString(R.string.media_unavailable))
                    .setMessage(getString(R.string.external_storage_unavailable_pls_))
                    .create();
        }

        disconnectDialog.show();
    }

    @Override
    public void showNoSpaceDialog() {
        if(noSpaceDialog == null) {
            noSpaceDialog = new AlertDialog.Builder(getContext())
                    .setTitle(getString(R.string.no_few_space_on_disk))
                    .setMessage(getString(R.string.your_have_no_space_available_pls_))
                    .create();
        }

        noSpaceDialog.show();

    }

    @Override
    public int availableDisk() {
        File envFilePath = getEnvFilePath();
        long freeSpace = envFilePath.getFreeSpace();
        int mb = Math.round(freeSpace / 1048576);
        return mb;
    }

    @Override
    public File newFile() {
        Calendar cal = Calendar.getInstance();
        long timeInMillis = cal.getTimeInMillis();

        String filename = String.valueOf(timeInMillis) + ".jpeg";

        File envFilePath = getEnvFilePath();

        try {
            File newFile = new File(envFilePath.getAbsolutePath(), filename);
            newFile.createNewFile();

            return newFile;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void showErrorDialog() {
        if(errorDialog == null) {
            errorDialog = new AlertDialog.Builder(getContext())
                    .setTitle(getString(R.string.file_system_error))
                    .setMessage(getString(R.string.something_went_wrong_during_file_))
                    .create();
        }

        errorDialog.show();
    }
}
