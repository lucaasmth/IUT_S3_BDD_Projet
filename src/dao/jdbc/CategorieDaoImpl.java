package dao.jdbc;//package dao.jdbc;

import dao.exception.DaoException;
import model.Entity;
import model.Categorie;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class CategorieDaoImpl extends JdbcDao {

    public CategorieDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Collection<Entity> findAll() throws DaoException {
        Collection<Entity> categories = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM categorie");

            while (resultSet.next()) {
                Categorie categorie = new Categorie();
                categorie.setId(resultSet.getInt("idcategorie"));
                categorie.setLibelle(resultSet.getString("libellecategorie"));
                categories.add(categorie);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return categories;
    }

    @Override
    public Entity findById(int id) throws DaoException {
        Categorie categorie = null;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM categorie WHERE idcategorie="+id);

            if(resultSet.next()){
                categorie = new Categorie();
                categorie.setId(resultSet.getInt("idcategorie"));
                categorie.setLibelle(resultSet.getString("libellecategorie"));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return categorie;
    }

    @Override
    public void create(Entity entity) throws DaoException {

        Categorie categorie = (Categorie) entity;

        PreparedStatement stmt= null;

        String sqlReq = "insert into categorie(libellecategorie) values (?)";

        try {

            stmt = connection.prepareStatement(sqlReq);

            stmt.setString(1, categorie.getLibelle());

            int res = stmt.executeUpdate();
            if (res > 0) {
                System.out.println("Ligne insérée");

                // Donne le bon id à l'instance d'entity
                if (categorie.getId() == 0) {
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT MAX(idcategorie) AS m FROM categorie");
                    if (resultSet.next()) {
                        int max = resultSet.getInt("m");
                        categorie.setId(max);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getLocalizedMessage());
        }
    }

    @Override
    public void update(Entity entity) throws DaoException {
        Categorie categorie = (Categorie) entity;
        PreparedStatement stmt;
        String sqlReq = "UPDATE categorie SET libelle = ? WHERE idcategorie = ?";

        try {
            stmt = connection.prepareStatement(sqlReq);

            stmt.setString(1, categorie.getLibelle());
            stmt.setInt(2, categorie.getId());

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

        Categorie categorie = (Categorie) entity;

        Statement statement = null;
        try {
            statement = connection.createStatement();
            String sqlReq = "DELETE FROM categorie WHERE idcategorie = "+categorie.getId();

            int res = statement.executeUpdate(sqlReq);
            if (res > 0) System.out.println("ligne supprimé");
            else System.out.println("categorie introuvable");

        } catch(SQLException e) {
            System.err.println("Erreur SQL : " + e.getLocalizedMessage());
        }

    }

}