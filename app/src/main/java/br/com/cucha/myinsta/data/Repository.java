package br.com.cucha.myinsta.data;

import java.util.List;

import br.com.cucha.myinsta.data.PhotoDAO;

/**
 * Created by eduardocucharro on 13/03/17.
 */

public interface Repository {
    List<PhotoDAO> listAll();
}
