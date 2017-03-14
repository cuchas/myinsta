package br.com.cucha.myinsta;

import android.support.annotation.NonNull;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import br.com.cucha.myinsta.data.PhotoDAO;
import br.com.cucha.myinsta.data.Repository;
import br.com.cucha.myinsta.data.RepositoryImpl;
import br.com.cucha.myinsta.image.ImageService;
import br.com.cucha.myinsta.image.ImageServiceImpl;
import br.com.cucha.myinsta.image.Photo;

import static org.junit.Assert.assertTrue;

/**
 * Created by eduardocucharro on 13/03/17.
 */

public class ImageServiceTest {
    @Test
    public void listAll_return_empty_list_whenNoData() {
        Repository repository = new RepositoryImpl();
        ImageService service = new ImageServiceImpl(repository);

        List<Photo> list = service.listAll();

        assertTrue(list.isEmpty());
    }

    @Test
    public void listAll_return_desc_ordered() {

        List<PhotoDAO> dao = getFakePhotoDAO();

        Repository repository = new RepositoryImpl(dao);
        ImageService service = new ImageServiceImpl(repository);

        List<Photo> list = service.listAll();

        Photo p1 = list.get(0);
        Photo p2 = list.get(1);

        assertTrue(p1.date.after(p2.date));
    }

    @NonNull
    private List<PhotoDAO> getFakePhotoDAO() {
        Calendar cal = Calendar.getInstance();
        List<PhotoDAO> dao = new ArrayList<>();

        Random r = new Random(10);

        for(int i = 0; i < 20; i++) {
            int minutes = r.nextInt(1000);
            cal.set(Calendar.MINUTE, minutes);
            Date d = cal.getTime();
            PhotoDAO photoDAO = new PhotoDAO();
            photoDAO.setDate(d.getTime());
            dao.add(photoDAO);
        }
        return dao;
    }

    @Test
    public void listAll_return_limit_quantity_whenBigCollection() {

        List<PhotoDAO> fakeDAO = getFakePhotoDAO();

        Repository repository = new RepositoryImpl(fakeDAO);
        ImageService service = new ImageServiceImpl(repository);
        int expected = 15;

        List<Photo> list = service.listAll();

        assertTrue(expected == list.size());
    }

    @Test
    public void listNext_not_include_ref_image() {
        Repository repository = new RepositoryImpl();
        ImageService service = new ImageServiceImpl(repository);
        Photo photo = service.getPhotoById(10);

        List<Photo> list = service.listNext(Photo.getId());

        assertTrue(!list.contains(photo));
    }
}
