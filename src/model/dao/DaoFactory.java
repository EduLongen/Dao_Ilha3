package model.dao;

import db.DB;

import model.dao.impl.ItemDaoJDBC;
import model.dao.impl.TipoItemDaoJDBC;
import model.dao.impl.UsuarioDaoJDBC;

public class DaoFactory {

    public static UsuarioDao createUsuarioDao() {
        return new UsuarioDaoJDBC(DB.getConnection());
    }
  /*  public static ItemDao createItemDao() {
        return new ItemDaoJDBC(DB.getConnection());
    }*/
    public static TipoItemDao createTipoItemDao() {
        return new TipoItemDaoJDBC(DB.getConnection());
    }

}
