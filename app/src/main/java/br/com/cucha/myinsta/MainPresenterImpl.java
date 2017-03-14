package br.com.cucha.myinsta;

import android.net.Uri;

import java.io.File;

import br.com.cucha.myinsta.image.ImageInteractor;
import br.com.cucha.myinsta.image.ImageService;

/**
 * Created by eduardocucharro on 13/03/17.
 */

class MainPresenterImpl implements MainPresenter {

    private final MainView view;
    private final ImageInteractor interactor;

    public MainPresenterImpl(MainView view, ImageInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void init() {
        // TODO: 13/03/17
        view.showEmptyList();
    }

    @Override
    public void cameraClick() {
        // TODO: 13/03/17
        if(view.checkPermission()) {
            view.startCamera(new File(""));
            return;
        }

        view.showPermissionDialog();
    }

    @Override
    public void saveImage(Uri uri) {

    }

    @Override
    public void permissionDenied() {
        if(view.shouldShowDialog()) view.showPermissionDialog();
        else view.showUnlockPermissionsDialog();
    }
}
