package dao.jdbc;

import dao.exception.DaoException;
import model.*;
import model.Marque;
import model.Marque;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class MarqueDaoImpl extends JdbcDao {
    public MarqueDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Collection<Entity> findAll() throws DaoException {
        Collection<Entity> marques = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM marque");

            while (resultSet.next()) {
                Marque marque = new Marque();
                marque.setId(resultSet.getInt("idmarque"));
                marque.setNom(resultSet.getString("nommarque"));
                marques.add(marque);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return marques;
    }

    @Override
    public Entity findById(int id) throws DaoException {
        Marque marque = null;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM marque WHERE idmarque="+id);

            if(resultSet.next()){
                marque = new Marque();
                marque.setId(resultSet.getInt("idmarque"));
                marque.setNom(resultSet.getString("nommarque"));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return marque;
    }

    @Override
    public void create(Entity entity) throws DaoException {
        Marque marque = (Marque) entity;

        PreparedStatement stmt= null;

        String sqlReq = "insert into marque(nommarque) values (?)";

        try {

            stmt = connection.prepareStatement(sqlReq);

            stmt.setString(1, marque.getNom());

            int res = stmt.executeUpdate();
            if (res > 0) {
                System.out.println("Ligne insérée");

                // Donne le bon id à l'instance d'entity
                if (marque.getId() == 0) {
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT MAX(idmarque) AS m FROM marque");
                    if (resultSet.next()) {
                        int max = resultSet.getInt("m");
                        marque.setId(max);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getLocalizedMessage());
        }
    }

    @Override
    public void update(Entity entity) throws DaoException {
        Marque marque = (Marque) entity;
        PreparedStatement stmt;
        String sqlReq = "UPDATE marque SET nommarque = ? WHERE idmarque = ?";

        try {
            stmt = connection.prepareStatement(sqlReq);

            stmt.setString(1, marque.getNom());
            stmt.setInt(2, marque.getId());

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
        Marque marque = (Marque) entity;

        Statement statement = null;
        try {
            statement = connection.createStatement();
            String sqlReq = "DELETE FROM marque WHERE idmarque = "+marque.getId();

            int res = statement.executeUpdate(sqlReq);
            if (res > 0) System.out.println("ligne supprimé");
            else System.out.println("marque introuvable");

        } catch(SQLException e) {
            System.err.println("Erreur SQL : " + e.getLocalizedMessage());
        }
    }
}
