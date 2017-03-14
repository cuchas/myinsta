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

    File getEnvFilePath();

    boolean isStorageMounted();

    void startCamera(File file);

    void onActivityResult(Uri uri);

    boolean shouldShowDialog();

    void showUnlockPermissionsDialog();

    String[] getPermissions();

    void showDisconnectFromPCDialog();

    void showNoSpaceDialog();

    int availableDisk();

    File newFile();

    void showErrorDialog();
}
