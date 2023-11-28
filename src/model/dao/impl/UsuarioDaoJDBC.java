package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import model.dao.UsuarioDao;
import model.entities.CurrentUser;
import model.entities.Usuario;

import javax.swing.*;

public class UsuarioDaoJDBC implements UsuarioDao {

    private Connection conn;

    public UsuarioDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Usuario findByEmail(String email, String senha) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM usuario WHERE Email = ? AND Senha = ?");
            st.setString(1, email);
            st.setString(2, senha);
            rs = st.executeQuery();
            if (rs.next()) {
                Usuario obj = new Usuario();
                obj.setId(rs.getInt("Id"));
                obj.setNome(rs.getString("Nome"));
                obj.setSobrenome(rs.getString("Sobrenome"));
                obj.setEmail(rs.getString("Email"));
                obj.setSenha(rs.getString("Senha"));
                return obj;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public Usuario findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM usuario WHERE Id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                Usuario obj = new Usuario();
                obj.setId(rs.getInt("Id"));
                obj.setNome(rs.getString("Nome"));
                obj.setSobrenome(rs.getString("Sobrenome"));
                obj.setEmail(rs.getString("Email"));
                obj.setSenha(rs.getString("Senha"));
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

    @Override
    public List<Usuario> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM usuario ORDER BY Nome");
            rs = st.executeQuery();

            List<Usuario> list = new ArrayList<>();

            while (rs.next()) {
                Usuario obj = new Usuario();
                obj.setId(rs.getInt("Id"));
                obj.setNome(rs.getString("Nome"));
                obj.setSobrenome(rs.getString("Sobrenome"));
                obj.setEmail(rs.getString("Email"));
                obj.setSenha(rs.getString("Senha"));
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
    public void insert(Usuario obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO usuario " +
                            "(Nome, Sobrenome, Email, Senha) " +
                            "VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getNome());
            st.setString(2, obj.getSobrenome());
            st.setString(3, obj.getEmail());
            st.setString(4, obj.getSenha());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
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
    public void update(Usuario obj) {
        PreparedStatement st = null;
        try {
            Integer currentUserId = CurrentUser.getUserId();

            // Check if the current user ID is not null and has the right to update
            if (currentUserId != null && (currentUserId.equals(obj.getId()) || currentUserId.equals(1))) {
                st = conn.prepareStatement(
                        "UPDATE usuario " +
                                "SET Nome = ?, " +
                                "Sobrenome = ?, " +
                                "Email = ?, " +
                                "Senha = ? " +
                                "WHERE Id = ?");

                st.setString(1, obj.getNome());
                st.setString(2, obj.getSobrenome());
                st.setString(3, obj.getEmail());
                st.setString(4, obj.getSenha());
                st.setInt(5, obj.getId());

                st.executeUpdate();
            } else {
                // Display an error message to the user
                JOptionPane.showMessageDialog(null,
                        "Permission denied. You can only update your own account.",
                        "Update Error", JOptionPane.ERROR_MESSAGE);

                // You may also throw a DbException here if needed
                throw new DbException("Permission denied. You can only update your own account.");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            Integer currentUserId = CurrentUser.getUserId();

            // Check if the current user ID is not null and has the right to delete
            if (currentUserId != null && (currentUserId.equals(id) || currentUserId == 1)) {
                st = conn.prepareStatement(
                        "DELETE FROM usuario WHERE Id = ?");

                st.setInt(1, id);

                st.executeUpdate();
            } else {
                throw new DbException("Permission denied. You can only delete your own account.");
            }
        } catch (SQLException e) {
            throw new DbIntegrityException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

}