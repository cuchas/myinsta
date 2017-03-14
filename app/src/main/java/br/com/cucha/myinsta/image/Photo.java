package br.com.cucha.myinsta.image;

import java.util.Date;

/**
 * Created by eduardocucharro on 13/03/17.
 */

public class Photo {
    public Date date;
    private static Object id;

    public static Object getId() {
        return id;
    }
}
