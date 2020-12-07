package dao.jdbc;//package dao.jdbc;

import dao.exception.DaoException;
import model.Contrat;
import model.Entity;
import model.Facture;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class FactureDaoImpl extends JdbcDao {

    private ContratDaoImpl contratDao;

    public FactureDaoImpl(Connection connection) {
        super(connection);
        contratDao = new ContratDaoImpl(connection);
    }

    @Override
    public Collection<Entity> findAll() throws DaoException {
        Collection<Entity> factures = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM facture");

            while (resultSet.next()) {
                Facture facture = new Facture();
                facture.setId(resultSet.getInt("idfacture"));
                facture.setMontant(resultSet.getDouble("montant"));
                facture.setContrat((Contrat) contratDao.findById(resultSet.getInt("idcontrat")));
                factures.add(facture);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return factures;
    }

    @Override
    public Entity findById(int id) throws DaoException {
        Facture facture = null;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM facture WHERE idfacture="+id);

            if(resultSet.next()){
                facture = new Facture();
                facture.setId(resultSet.getInt("idfacture"));
                facture.setMontant(resultSet.getDouble("montant"));
                facture.setContrat((Contrat) contratDao.findById(resultSet.getInt("idcontrat")));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return facture;
    }

    @Override
    public void create(Entity entity) throws DaoException {

        Facture facture = (Facture) entity;

        PreparedStatement stmt= null;

        String sqlReq = "insert into facture(montant, idcontrat) values (?, ?)";

        try {

            stmt = connection.prepareStatement(sqlReq);

            stmt.setDouble(1, facture.getMontant());
            stmt.setInt(2, facture.getContrat().getId());

            int res = stmt.executeUpdate();
            if (res > 0) {
                System.out.println("Ligne insérée");

                // Donne le bon id à l'instance d'entity
                if (facture.getId() == 0) {
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT MAX(idfacture) AS m FROM facture");
                    if (resultSet.next()) {
                        int max = resultSet.getInt("m");
                        facture.setId(max);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getLocalizedMessage());
        }
    }

    @Override
    public void update(Entity entity) throws DaoException {
        Facture facture = (Facture) entity;
        PreparedStatement stmt;
        String sqlReq = "UPDATE facture SET montant = ?, idcontrat = ? WHERE idfacture = ?";

        try {
            stmt = connection.prepareStatement(sqlReq);

            stmt.setDouble(1, facture.getMontant());
            stmt.setInt(2, facture.getContrat().getId());
            stmt.setInt(3, facture.getId());

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

        Facture facture = (Facture) entity;

        Statement statement = null;
        try {
            statement = connection.createStatement();
            String sqlReq = "DELETE FROM facture WHERE idfacture = "+facture.getId();

            int res = statement.executeUpdate(sqlReq);
            if (res > 0) System.out.println("ligne supprimé");
            else System.out.println("facture introuvable");

        } catch(SQLException e) {
            System.err.println("Erreur SQL : " + e.getLocalizedMessage());
        }

    }

}