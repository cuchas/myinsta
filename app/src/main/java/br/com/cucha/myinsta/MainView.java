package br.com.cucha.myinsta;

import android.net.Uri;

import java.io.File;

/**
 * Created by eduardocucharro on 13/03/17.
 */

interface MainView {
    void showEmptyList();

    boolean checkPermission();

    void showPermissionDialog();

    void startCamera(File file);

    void onActivityResult(Uri uri);

    void destroy();

    boolean shouldShowDialog();

    void showUnlockPermissionsDialog();
}
