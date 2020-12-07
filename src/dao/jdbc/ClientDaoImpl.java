package dao.jdbc;

import dao.exception.DaoException;
import model.*;
import model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class ClientDaoImpl extends JdbcDao {
    private final VilleDaoImpl villeDao;

    public ClientDaoImpl(Connection connection) {
        super(connection);
        villeDao = new VilleDaoImpl(connection);
    }

    @Override
    public Collection<Entity> findAll() throws DaoException {
        Collection<Entity> clients = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM client");

            while (resultSet.next()) {
                Client client = new Client();
                client.setId(resultSet.getInt("idclient"));
                client.setNom(resultSet.getString("nomclient"));
                client.setAdresse(resultSet.getString("adresseclient"));
                client.setCodePostal(resultSet.getInt("codepostalclient"));
                client.setVille((Ville) villeDao.findById(resultSet.getInt("idville")));
                clients.add(client);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return clients;
    }

    @Override
    public Entity findById(int id) throws DaoException {
        Client client = null;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM client WHERE idclient="+id);

            if(resultSet.next()){
                client = new Client();
                client.setId(resultSet.getInt("idclient"));
                client.setNom(resultSet.getString("nomclient"));
                client.setAdresse(resultSet.getString("adresseclient"));
                client.setCodePostal(resultSet.getInt("codepostalclient"));
                client.setVille((Ville) villeDao.findById(resultSet.getInt("idville")));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return client;
    }

    @Override
    public void create(Entity entity) throws DaoException {
        Client client = (Client) entity;

        PreparedStatement stmt= null;

        String sqlReq = "INSERT INTO client(nomclient, adresseclient, codepostalclient, idville) VALUES (?, ?, ?, ?)";

        try {

            stmt = connection.prepareStatement(sqlReq);

            stmt.setString(1, client.getNom());
            stmt.setString(2, client.getAdresse());
            stmt.setInt(3, client.getCodePostal());
            stmt.setInt(4, client.getVille().getId());

            int res = stmt.executeUpdate();
            if (res > 0) {
                System.out.println("Ligne insérée");

                // Donne le bon id à l'instance d'entity
                if (client.getId() == 0) {
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT MAX(idclient) AS m FROM client");
                    if (resultSet.next()) {
                        int max = resultSet.getInt("m");
                        client.setId(max);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getLocalizedMessage());
        }
    }

    @Override
    public void update(Entity entity) throws DaoException {
        Client client = (Client) entity;
        PreparedStatement stmt;
        String sqlReq = "UPDATE client SET nomclient = ?, adresseclient = ?, codepostalclient = ?, idville = ? WHERE idclient = ?";

        try {
            stmt = connection.prepareStatement(sqlReq);

            stmt.setString(1, client.getNom());
            stmt.setString(2, client.getAdresse());
            stmt.setInt(3, client.getCodePostal());
            stmt.setInt(4, client.getVille().getId());
            stmt.setInt(5, client.getId());

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
        Client client = (Client) entity;

        Statement statement = null;
        try {
            statement = connection.createStatement();
            String sqlReq = "DELETE FROM client WHERE idclient = "+client.getId();

            int res = statement.executeUpdate(sqlReq);
            if (res > 0) System.out.println("ligne supprimé");
            else System.out.println("client introuvable");

        } catch(SQLException e) {
            System.err.println("Erreur SQL : " + e.getLocalizedMessage());
        }
    }
}
