package dao.jdbc;

import dao.exception.DaoException;
import model.*;
import model.Contrat;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class ContratDaoImpl extends JdbcDao {
    private final ClientDaoImpl clientDao;
    private final VehiculeDaoImpl vehiculeDao;
    private final AgenceDaoImpl agenceDao;

    public ContratDaoImpl(Connection connection) {
        super(connection);
        this.clientDao = new ClientDaoImpl(connection);
        this.vehiculeDao = new VehiculeDaoImpl(connection);
        this.agenceDao = new AgenceDaoImpl(connection);
    }

    @Override
    public Collection<Entity> findAll() throws DaoException {
        Collection<Entity> contrats = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM contrat");

            while (resultSet.next()) {
                Contrat contrat = new Contrat();
                contrat.setId(resultSet.getInt("idcontrat"));
                contrat.setDateRetour(resultSet.getDate("datederetrait"));
                contrat.setDateRetrait(resultSet.getDate("datederetour"));
                contrat.setKmRetrait(resultSet.getInt("kmretrait"));
                contrat.setKmRetour(resultSet.getInt("kmretour"));
                contrat.setClient((Client) clientDao.findById(resultSet.getInt("idclient")));
                contrat.setVehicule((Vehicule) vehiculeDao.findById( Integer.parseInt(resultSet.getString("immatriculation"))) );
                contrat.setAgenceRetour((Agence) agenceDao.findById(resultSet.getInt("idagencederetour")));
                contrats.add(contrat);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return contrats;
    }

    @Override
    public Entity findById(int id) throws DaoException {
        Contrat contrat = null;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM contrat WHERE idcontrat="+id);

            if(resultSet.next()){
                contrat = new Contrat();
                contrat.setId(resultSet.getInt("idcontrat"));
                contrat.setDateRetour(resultSet.getDate("datederetrait"));
                contrat.setDateRetrait(resultSet.getDate("datederetour"));
                contrat.setKmRetrait(resultSet.getInt("kmretrait"));
                contrat.setKmRetour(resultSet.getInt("kmretour"));
                contrat.setClient((Client) clientDao.findById(resultSet.getInt("idclient")));
                contrat.setVehicule((Vehicule) vehiculeDao.findById( Integer.parseInt(resultSet.getString("immatriculation"))) );
                contrat.setAgenceRetour((Agence) agenceDao.findById(resultSet.getInt("idagencederetour")));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return contrat;
    }

    @Override
    public void create(Entity entity) throws DaoException {
        Contrat contrat = (Contrat) entity;

        PreparedStatement stmt= null;

        String sqlReq = "INSERT INTO contrat(datederetrait, datederetour, kmretrait, kmretour, idclient, immatriculation, idagencederetour) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {

            stmt = connection.prepareStatement(sqlReq);

            stmt.setDate(1, contrat.getDateRetrait());
            stmt.setDate(2, contrat.getDateRetour());
            stmt.setInt(3, contrat.getKmRetrait());
            stmt.setInt(4, contrat.getKmRetour());
            stmt.setInt(5, contrat.getClient().getId());
            stmt.setString(6, contrat.getVehicule().getImmatriculation());
            stmt.setInt(7, contrat.getAgenceRetour().getId());

            int res = stmt.executeUpdate();
            if (res > 0) {
                System.out.println("Ligne insérée");

                // Donne le bon id à l'instance d'entity
                if (contrat.getId() == 0) {
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT MAX(idcontrat) AS m FROM contrat");
                    if (resultSet.next()) {
                        int max = resultSet.getInt("m");
                        contrat.setId(max);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getLocalizedMessage());
        }
    }

    @Override
    public void update(Entity entity) throws DaoException {
        Contrat contrat = (Contrat) entity;
        PreparedStatement stmt;
        String sqlReq = "UPDATE contrat SET datederetrait = ?, datederetour = ?, kmretrait = ?, kmretour = ?, idclient = ?, immatriculation = ?, idagencederetour = ? WHERE idcontrat = ?";

        try {
            stmt = connection.prepareStatement(sqlReq);

            stmt.setDate(1, contrat.getDateRetrait());
            stmt.setDate(2, contrat.getDateRetour());
            stmt.setInt(3, contrat.getKmRetrait());
            stmt.setInt(4, contrat.getKmRetour());
            stmt.setInt(5, contrat.getClient().getId());
            stmt.setString(6, contrat.getVehicule().getImmatriculation());
            stmt.setInt(7, contrat.getAgenceRetour().getId());

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
        Contrat contrat = (Contrat) entity;

        Statement statement = null;
        try {
            statement = connection.createStatement();
            String sqlReq = "DELETE FROM contrat WHERE idcontrat = "+contrat.getId();

            int res = statement.executeUpdate(sqlReq);
            if (res > 0) System.out.println("ligne supprimé");
            else System.out.println("contrat introuvable");

        } catch(SQLException e) {
            System.err.println("Erreur SQL : " + e.getLocalizedMessage());
        }

    }
}
