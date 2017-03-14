package br.com.cucha.myinsta;

import android.net.Uri;

import java.io.File;

/**
 * Created by eduardocucharro on 13/03/17.
 */

interface MainPresenter {
    void init();

    void cameraClick();

    void saveImage(Uri uri);

    void permissionDenied();
}
