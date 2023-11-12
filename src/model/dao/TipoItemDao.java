package model.dao;

import model.entities.TipoItem;


import java.util.List;

public interface TipoItemDao {
    void insert(TipoItem obj);
    void update(TipoItem obj);
    void deleteById(Integer id);
    TipoItem findById(Integer id);
    List<TipoItem> findAll();
}
