package br.com.cucha.myinsta.image;

import android.content.Context;

import java.util.List;

import br.com.cucha.myinsta.image.ImageInteractor;

/**
 * Created by eduardocucharro on 13/03/17.
 */

public class ImageInteractorImpl implements ImageInteractor {
    public ImageInteractorImpl(Context applicationContext) {
    }

    @Override
    public void onListAllSuccess(List<Photo> list) {

    }

    @Override
    public Void listAll() {
        return null;
    }
}
