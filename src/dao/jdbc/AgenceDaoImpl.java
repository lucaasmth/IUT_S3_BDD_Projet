package dao.jdbc;

import dao.exception.DaoException;
import model.*;
import model.Agence;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class AgenceDaoImpl extends JdbcDao {

    private final VilleDaoImpl villeDao;

    public AgenceDaoImpl(Connection connection) {
        super(connection);
        villeDao = new VilleDaoImpl(connection);
    }

    @Override
    public Collection<Entity> findAll() throws DaoException {
        Collection<Entity> agences = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM agence");

            while (resultSet.next()) {
                Agence agence = new Agence();
                agence.setId(resultSet.getInt("idagence"));
                agence.setNbEmployes(resultSet.getInt("nbemployees"));
                agence.setVille((Ville) villeDao.findById(resultSet.getInt("idagence")));
                agences.add(agence);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return agences;
    }

    @Override
    public Entity findById(int id) throws DaoException {
        Agence agence = null;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM agence WHERE idagence="+id);

            if(resultSet.next()){
                agence = new Agence();
                agence.setId(resultSet.getInt("idagence"));
                agence.setNbEmployes(resultSet.getInt("nbemployees"));
                agence.setVille((Ville) villeDao.findById(resultSet.getInt("idagence")));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return agence;
    }

    @Override
    public void create(Entity entity) throws DaoException {
        Agence agence = (Agence) entity;

        PreparedStatement stmt= null;

        String sqlReq = "INSERT INTO agence(nbemployees, idville) VALUES (?, ?)";

        try {

            stmt = connection.prepareStatement(sqlReq);

            stmt.setInt(1, agence.getNbEmployes());
            stmt.setInt(2, agence.getVille().getId());

            int res = stmt.executeUpdate();
            if (res > 0) {
                System.out.println("Ligne insérée");

                // Donne le bon id à l'instance d'entity
                if (agence.getId() == 0) {
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT MAX(idagence) AS m FROM agence");
                    if (resultSet.next()) {
                        int max = resultSet.getInt("m");
                        agence.setId(max);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getLocalizedMessage());
        }
    }

    @Override
    public void update(Entity entity) throws DaoException {
        Agence agence = (Agence) entity;
        PreparedStatement stmt;
        String sqlReq = "UPDATE agence SET nbemployees = ?, idville = ? WHERE idagence = ?";

        try {
            stmt = connection.prepareStatement(sqlReq);

            stmt.setInt(1, agence.getNbEmployes());
            stmt.setInt(2, agence.getVille().getId());
            stmt.setInt(3, agence.getId());

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
        Agence agence = (Agence) entity;

        Statement statement = null;
        try {
            statement = connection.createStatement();
            String sqlReq = "DELETE FROM agence WHERE idagence = "+agence.getId();

            int res = statement.executeUpdate(sqlReq);
            if (res > 0) System.out.println("ligne supprimé");
            else System.out.println("agence introuvable");

        } catch(SQLException e) {
            System.err.println("Erreur SQL : " + e.getLocalizedMessage());
        }
    }
}
