package br.com.cucha.myinsta.image;

import java.util.List;

/**
 * Created by eduardocucharro on 13/03/17.
 */

public interface ImageService {
    List<Photo> listAll();

    Photo getPhotoById(int i);

    List<Photo> listNext(Object id);
}
