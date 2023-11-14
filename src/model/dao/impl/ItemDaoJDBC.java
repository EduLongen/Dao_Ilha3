package model.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;

import model.dao.ItemDao;
import model.entities.TipoItem;
import model.entities.Item;


public class ItemDaoJDBC implements ItemDao {
    private Connection conn;

    public ItemDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Item obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO item "
                            + "(Descricao, Marca, Modelo, NumeroSerie, Potencia, Localizacao, Enviado, NotaFiscal, DataEntrada, UltimaQualificacao, ProximaQualifacao, TipoitemId) "
                            + "VALUES "
                            + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getDescricao());
            st.setString(2, obj.getMarca());
            st.setString(3, obj.getModelo());
            st.setString(4, obj.getNumeroSerie());
            st.setString(5, obj.getPotencia());
            st.setString(6, obj.getLocalizacao());
            st.setString(7, obj.getEnviado());
            st.setString(8, obj.getNotaFiscal());

            st.setDate(9, new java.sql.Date(obj.getDataEntrada().getTime()));
            st.setDate(10, new java.sql.Date(obj.getUltimaQualificacao().getTime()));
            st.setDate(11, new java.sql.Date(obj.getProximaQualifacao().getTime()));

            st.setInt(12, obj.getTipoItem().getId());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(rs);
            }
            else {
                throw new DbException("Unexpected error! No rows affected!");
            }
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }


    @Override
    public void update(Item obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE item "
                            + "SET Descricao = ?, Marca = ?, Modelo = ?, NumeroSerie = ?, Potencia = ?, Localizacao = ?, Enviado = ?, NotaFiscal = ?, DataEntrada = ?, UltimaQualificacao = ?, ProximaQualifacao = ?, TipoitemId = ? "
                            + "WHERE Id = ?");

            st.setString(1, obj.getDescricao());
            st.setString(2, obj.getMarca());
            st.setString(3, obj.getModelo());
            st.setString(4, obj.getNumeroSerie());
            st.setString(5, obj.getPotencia());
            st.setString(6, obj.getLocalizacao());
            st.setString(7, obj.getEnviado());
            st.setString(8, obj.getNotaFiscal());

            st.setDate(9, new java.sql.Date(obj.getDataEntrada().getTime()));
            st.setDate(10, new java.sql.Date(obj.getUltimaQualificacao().getTime()));
            st.setDate(11, new java.sql.Date(obj.getProximaQualifacao().getTime()));

            st.setInt(12, obj.getTipoItem().getId());
            st.setInt(13, obj.getId());

            st.executeUpdate();
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM item WHERE Id = ?");

            st.setInt(1, id);

            st.executeUpdate();
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Item findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT item.*,tipoitem.Tipo as Tipo "
                            + "FROM item INNER JOIN tipoitem "
                            + "ON item.TipoitemId = tipoitem.Id "
                            + "WHERE item.Id = ?");

            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                TipoItem tipo = instantiateTipoItem(rs);
                Item obj = instantiateItem(rs, tipo);
                return obj;
            }
            return null;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }


    private Item instantiateItem(ResultSet rs, TipoItem tipo) throws SQLException {
        Item obj = new Item();
        obj.setId(rs.getInt("Id"));
        obj.setDescricao(rs.getString("Descricao"));
        obj.setMarca(rs.getString("Marca"));
        obj.setModelo(rs.getString("Modelo"));
        obj.setNumeroSerie(rs.getString("NumeroSerie"));
        obj.setPotencia(rs.getString("Potencia"));
        obj.setLocalizacao(rs.getString("Localizacao"));
        obj.setEnviado(rs.getString("Enviado"));
        obj.setNotaFiscal(rs.getString("NotaFiscal"));

        obj.setDataEntrada(rs.getDate("DataEntrada"));
        obj.setUltimaQualificacao(rs.getDate("UltimaQualificacao"));
        obj.setProximaQualifacao(rs.getDate("ProximaQualifacao"));

        obj.setTipoItem(tipo);
        return obj;
    }

    private TipoItem instantiateTipoItem(ResultSet rs) throws SQLException {
        TipoItem tipo = new TipoItem();
        tipo.setId(rs.getInt("TipoitemId"));
        tipo.setTipo(rs.getString("Tipo"));
        return tipo;
    }

    @Override
    public List<Item> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT item.*,tipoitem.Tipo as Tipo "
                            + "FROM item INNER JOIN tipoitem "
                            + "ON item.TipoitemId = tipoitem.Id "
                            + "ORDER BY Tipo");

            rs = st.executeQuery();

            List<Item> list = new ArrayList<>();
            Map<Integer, TipoItem> map = new HashMap<>();

            while (rs.next()) {

                TipoItem tipo = map.get(rs.getInt("TipoitemId"));

                if (tipo == null) {
                    tipo = instantiateTipoItem(rs);
                    map.put(rs.getInt("TipoitemId"), tipo);
                }

                Item obj = instantiateItem(rs, tipo);
                list.add(obj);
            }
            return list;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Item> findByTipoItem(TipoItem tipoItem) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT item.*,tipoitem.Tipo as Tipo "
                            + "FROM item INNER JOIN tipoitem "
                            + "ON item.TipoitemId = tipoitem.Id "
                            + "WHERE TipoitemId = ? "
                            + "ORDER BY Tipo");

            st.setInt(1, tipoItem.getId());

            rs = st.executeQuery();

            List<Item> list = new ArrayList<>();
            Map<Integer, TipoItem> map = new HashMap<>();

            while (rs.next()) {

                TipoItem tipo = map.get(rs.getInt("TipoitemId"));

                if (tipo == null) {
                    tipo = instantiateTipoItem(rs);
                    map.put(rs.getInt("TipoitemId"), tipo);
                }

                Item obj = instantiateItem(rs, tipo);
                list.add(obj);
            }
            return list;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}

