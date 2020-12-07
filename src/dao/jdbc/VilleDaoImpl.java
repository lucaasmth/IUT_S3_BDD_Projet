package dao.jdbc;

import dao.exception.DaoException;
import model.Entity;
import model.Ville;
import model.Ville;
import model.Ville;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class VilleDaoImpl extends JdbcDao {
    public VilleDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Collection<Entity> findAll() throws DaoException {
        Collection<Entity> villes = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ville");

            while (resultSet.next()) {
                Ville ville = new Ville();
                ville.setId(resultSet.getInt("idville"));
                ville.setNom(resultSet.getString("nomville"));
                ville.setNombreHabitants(resultSet.getInt("nombrehabitants"));
                villes.add(ville);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return villes;
    }

    @Override
    public Entity findById(int id) throws DaoException {
        Ville ville = null;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ville WHERE idville="+id);

            if(resultSet.next()){
                ville = new Ville();
                ville.setId(resultSet.getInt("idville"));
                ville.setNom(resultSet.getString("nomville"));
                ville.setNombreHabitants(resultSet.getInt("nombrehabitants"));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return ville;
    }

    @Override
    public void create(Entity entity) throws DaoException {
        Ville ville = (Ville) entity;

        PreparedStatement stmt= null;

        String sqlReq = "insert into ville(nomville, nombrehabitants) values (?, ?)";

        try {

            stmt = connection.prepareStatement(sqlReq);

            stmt.setString(1, ville.getNom());
            stmt.setInt(2, ville.getNombreHabitants());

            int res = stmt.executeUpdate();
            if (res > 0) {
                System.out.println("Ligne insérée");

                // Donne le bon id à l'instance d'entity
                if (ville.getId() == 0) {
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT MAX(idville) AS m FROM ville");
                    if (resultSet.next()) {
                        int max = resultSet.getInt("m");
                        ville.setId(max);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getLocalizedMessage());
        }
    }

    @Override
    public void update(Entity entity) throws DaoException {
        Ville ville = (Ville) entity;
        PreparedStatement stmt;
        String sqlReq = "UPDATE ville SET nomville = ?, nombrehabitants = ? WHERE idville = ?";

        try {
            stmt = connection.prepareStatement(sqlReq);

            stmt.setString(1, ville.getNom());
            stmt.setInt(2, ville.getNombreHabitants());
            stmt.setInt(3, ville.getId());

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
        Ville ville = (Ville) entity;

        Statement statement = null;
        try {
            statement = connection.createStatement();
            String sqlReq = "DELETE FROM ville WHERE idville = "+ville.getId();

            int res = statement.executeUpdate(sqlReq);
            if (res > 0) System.out.println("ligne supprimé");
            else System.out.println("ville introuvable");

        } catch(SQLException e) {
            System.err.println("Erreur SQL : " + e.getLocalizedMessage());
        }
    }
}
