package br.com.cucha.myinsta.image;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import br.com.cucha.myinsta.data.PhotoDAO;
import br.com.cucha.myinsta.data.Repository;

/**
 * Created by eduardocucharro on 13/03/17.
 */

public class ImageServiceImpl implements ImageService {

    private final Repository repository;

    public ImageServiceImpl(Repository repository) {
        this.repository = repository;
    }

    @Override
    public List<Photo> listAll() {
        // TODO: 13/03/17
        List<PhotoDAO> daoList = repository.listAll();

        Collections.sort(daoList, new PhotoComparator());

        List<Photo> list = new ArrayList<>(daoList.size());

        for (PhotoDAO p : daoList) {
            Photo photo = new Photo();
            photo.date = new Date(p.getDate());
            list.add(photo);
        }

        return list.size() > 0 ? list.subList(0, 15) : list;
    }

    @Override
    public Photo getPhotoById(int i) {
        // TODO: 13/03/17
        return new Photo();
    }

    @Override
    public List<Photo> listNext(Object id) {
        // TODO: 13/03/17
        return new ArrayList<>();
    }

    private class PhotoComparator implements Comparator<PhotoDAO> {
        @Override
        public int compare(PhotoDAO o1, PhotoDAO o2) {
            Date d1 = new Date(o1.getDate());
            Date d2 = new Date(o2.getDate());

            boolean eq = d1.equals(d2);

            if(eq) return 0;

            boolean before = d1.before(d2);

            return before ? 1 : -1;
        }
    }
}
