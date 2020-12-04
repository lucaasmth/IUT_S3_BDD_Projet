package dao.jdbc;//package dao.jdbc;

import dao.exception.DaoException;
import model.Entity;
import model.Modele;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class ModeleDaoImpl extends JdbcDao {

    public ModeleDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Collection<Entity> findAll() throws DaoException {
        Collection<Entity> modeles = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM modele");

            while (resultSet.next()) {
                Modele modele = new Modele();
                modele.setId(resultSet.getInt("idmodele"));
                modele.setDenomination(resultSet.getString("denomination"));
                modele.setPuissancefiscale(resultSet.getInt("puissancefiscale"));
                modeles.add(modele);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return modeles;
    }

    @Override
    public Entity findById(int id) throws DaoException {
        Modele modele = null;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM modele WHERE idmodele="+id);

            if(resultSet.next()){
                modele = new Modele();
                modele.setId(resultSet.getInt("idmodele"));
                modele.setDenomination(resultSet.getString("denomination"));
                modele.setPuissancefiscale(resultSet.getInt("puissancefiscale"));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return modele;
    }

    @Override
    public void create(Entity entity) throws DaoException {

        Modele modele = (Modele) entity;

        PreparedStatement stmt= null;

        String sqlReq = "insert into modele(denomination, puissancefiscale) values (?, ?)";

        try {

            stmt = connection.prepareStatement(sqlReq);

            stmt.setString(1, modele.getDenomination());
            stmt.setInt(2, modele.getPuissancefiscale());

            int res = stmt.executeUpdate();
            if (res > 0) {
                System.out.println("Ligne insérée");

                // Donne le bon id à l'instance d'entity
                if (modele.getId() == 0) {
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT MAX(idmodele) AS m FROM modele");
                    if (resultSet.next()) {
                        int max = resultSet.getInt("m");
                        modele.setId(max);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getLocalizedMessage());
        }
    }

    @Override
    public void update(Entity entity) throws DaoException {
        Modele modele = (Modele) entity;
        PreparedStatement stmt;
        String sqlReq = "UPDATE modele SET denomination = ?, puissancefiscale = ? WHERE idmodele = ?";

        try {
            stmt = connection.prepareStatement(sqlReq);

            stmt.setString(1, modele.getDenomination());
            stmt.setInt(2, modele.getPuissancefiscale());
            stmt.setInt(3, modele.getId());

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

        Modele modele = (Modele) entity;

        Statement statement = null;
        try {
            statement = connection.createStatement();
            String sqlReq = "DELETE FROM modele WHERE idmodele = "+modele.getId();

            int res = statement.executeUpdate(sqlReq);
            if (res > 0) System.out.println("ligne supprimé");
            else System.out.println("modele introuvable");

        } catch(SQLException e) {
            System.err.println("Erreur SQL : " + e.getLocalizedMessage());
        }

    }

}