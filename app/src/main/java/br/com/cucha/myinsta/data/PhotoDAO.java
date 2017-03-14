package br.com.cucha.myinsta.data;

import java.util.Comparator;
import java.util.Date;

/**
 * Created by eduardocucharro on 13/03/17.
 */

public class PhotoDAO {
    private long date;

    public void setDate(long date) {
        this.date = date;
    }

    public long getDate() {
        return date;
    }

//    @Override
//    public int compare(PhotoDAO o1, PhotoDAO o2) {
//        Date d1 = new Date(o1.getDate());
//        Date d2 = new Date(o2.getDate());
//
//        boolean before = d1.before(d2);
//
//        return before ? -1 : 1;
//    }

//    @Override
//    public int compare(Object o1, Object o2) {
//        Date d1 = new Date(((PhotoDAO) o1).getDate());
//        Date d2 = new Date(((PhotoDAO) o2).getDate());
//
//        boolean before = d1.before(d2);
//
//        return before ? -1 : 1;
//    }
}
