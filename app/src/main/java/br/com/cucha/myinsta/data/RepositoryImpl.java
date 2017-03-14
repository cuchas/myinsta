package br.com.cucha.myinsta.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eduardocucharro on 13/03/17.
 */

public class RepositoryImpl implements Repository {
    private List<PhotoDAO> list;

    public RepositoryImpl(List<PhotoDAO> dao) {
        this.list = dao;
    }

    public RepositoryImpl() {

    }

    @Override
    public List<PhotoDAO> listAll() {
        return list == null ? new ArrayList<PhotoDAO>() : list;
    }
}
