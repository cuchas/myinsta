package br.com.cucha.myinsta;


import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;

import br.com.cucha.myinsta.image.ImageInteractor;
import br.com.cucha.myinsta.image.ImageInteractorImpl;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoListFragment extends Fragment implements MainView {

    final static String[] permissions = new String[] {
            Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE };

    private static final int REQUEST_CODE_PERMISSIONS = 1001;

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    public static final String TAG = PhotoListFragment.class.getName();
    private Unbinder unbinder;
    private MainPresenter presenter;

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
        String cameraPermission = Manifest.permission.CAMERA;
        String writePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE;

        int cameraCheck = ActivityCompat.checkSelfPermission(getContext(), cameraPermission);
        int writeCheck = ActivityCompat.checkSelfPermission(getContext(), writePermission);

        return cameraCheck == PackageManager.PERMISSION_GRANTED &&
                writeCheck == PackageManager.PERMISSION_GRANTED;

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
    public void showPermissionDialog() {
        ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_CODE_PERMISSIONS);
    }

    @Override
    public void startCamera(File file) {

    }

    @Override
    public void onActivityResult(Uri uri) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public boolean shouldShowDialog() {
        return false;
    }

    @Override
    public void showUnlockPermissionsDialog() {

    }
}
