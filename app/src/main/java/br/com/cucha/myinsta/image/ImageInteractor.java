package br.com.cucha.myinsta.image;

import java.util.List;

/**
 * Created by eduardocucharro on 13/03/17.
 */

public interface ImageInteractor {
    void onListAllSuccess(List<Photo> list);

    Void listAll();
}
