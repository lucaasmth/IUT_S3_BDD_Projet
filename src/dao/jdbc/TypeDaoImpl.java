package dao.jdbc;//package dao.jdbc;

import dao.exception.DaoException;
import model.Entity;
import model.Type;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class TypeDaoImpl extends JdbcDao {

    public TypeDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Collection<Entity> findAll() throws DaoException {
        Collection<Entity> types = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM type");

            while (resultSet.next()) {
                Type type = new Type();
                type.setId(resultSet.getInt("idtype"));
                type.setLibelle(resultSet.getString("libelletype"));
                types.add(type);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return types;
    }

    @Override
    public Entity findById(int id) throws DaoException {
        Type type = null;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM type WHERE idtype="+id);

            if(resultSet.next()){
                type = new Type();
                type.setId(resultSet.getInt("idtype"));
                type.setLibelle(resultSet.getString("libelletype"));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return type;
    }

    @Override
    public void create(Entity entity) throws DaoException {

        Type type = (Type) entity;

        PreparedStatement stmt= null;

        String sqlReq = "insert into type(libelletype) values (?)";

        try {

            stmt = connection.prepareStatement(sqlReq);

            stmt.setString(1, type.getLibelle());

            int res = stmt.executeUpdate();
            if (res > 0) {
                System.out.println("Ligne insérée");

                // Donne le bon id à l'instance d'entity
                if (type.getId() == 0) {
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT MAX(idtype) AS m FROM type");
                    if (resultSet.next()) {
                        int max = resultSet.getInt("m");
                        type.setId(max);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getLocalizedMessage());
        }
    }

    @Override
    public void update(Entity entity) throws DaoException {
        Type type = (Type) entity;
        PreparedStatement stmt;
        String sqlReq = "UPDATE type SET libelletype = ? WHERE idtype = ?";

        try {
            stmt = connection.prepareStatement(sqlReq);

            stmt.setString(1, type.getLibelle());
            stmt.setInt(2, type.getId());

            int res = stmt.executeUpdate();
            if (res > 0) {
                System.out.println("Ligne modifiée");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void delete(Entity entity) throws DaoException {

        Type type = (Type) entity;

        Statement statement = null;
        try {
            statement = connection.createStatement();
            String sqlReq = "DELETE FROM type WHERE idtype = "+type.getId();

            int res = statement.executeUpdate(sqlReq);
            if (res > 0) System.out.println("ligne supprimé");
            else System.out.println("type introuvable");

        } catch(SQLException e) {
            System.err.println("Erreur SQL : " + e.getLocalizedMessage());
        }

    }

}