package model.dao;

import model.entities.Item;
import model.entities.TipoItem;

import java.util.List;

public interface ItemDao {
    void insert(Item obj);
    void update(Item obj);
    void deleteById(Integer id);
    Item findById(Integer id);
    List<Item> findAll();
    List<Item> findByTipoItem(TipoItem tipoItem);
}
