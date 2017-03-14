package br.com.cucha.myinsta;

import android.net.Uri;

/**
 * Created by eduardocucharro on 13/03/17.
 */

interface MainPresenter {
    void init();

    void cameraClick();

    void saveImage(Uri uri);

    void permissionDenied();
}
