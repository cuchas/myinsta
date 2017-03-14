package br.com.cucha.myinsta;

import android.Manifest;

import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by eduardocucharro on 14/03/17.
 */

public class MainViewTest {
    @Test
    public void getPermissions_hasCameraPermission() {
//        String[] permissions = view.getPermissions();
//
//        List<String> list = Arrays.asList(permissions);
//
//        assertTrue(list.contains(Manifest.permission.CAMERA));

        fail();
    }

    @Test
    public void getPermission_hasWriteExternalPermission() {
//        String[] permissions = view.getPermissions();
//
//        List<String> list = Arrays.asList(permissions);
//
//        assertTrue(list.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE));

        fail();
    }

    @Test
    public void newFile_externalFile() {
//        MainPresenter presenter = new MainPresenterImpl(view, interactor);
//
//        File file = view.newFile();
//
//        assertTrue(file.getAbsolutePath().contains("Android/data/br.com.cucha.myinsta"));
        fail();
    }
}
