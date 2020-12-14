package dao.jdbc;//package dao.jdbc;

import dao.exception.DaoException;
import model.Categorie;
import model.Entity;
import model.Type;
import model.Agence;
import model.Marque;
import model.Modele;
import model.Vehicule;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class VehiculeDaoImpl extends JdbcDao {

    private MarqueDaoImpl marqueDao;
    private ModeleDaoImpl modeleDao;
    private CategorieDaoImpl categorieDao;
    private TypeDaoImpl typeDao;
    private AgenceDaoImpl agenceDao;

    public VehiculeDaoImpl(Connection connection) {
        super(connection);

        marqueDao = new MarqueDaoImpl(connection);
        modeleDao = new ModeleDaoImpl(connection);
        categorieDao = new CategorieDaoImpl(connection);
        typeDao = new TypeDaoImpl(connection);
        agenceDao = new AgenceDaoImpl(connection);
    }

    @Override
    public Collection<Entity> findAll() throws DaoException {
        Collection<Entity> vehicules = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM vehicule");

            while (resultSet.next()) {
                Vehicule vehicule = new Vehicule();
                vehicule.setImmatriculation(resultSet.getString("immatriculation"));
                vehicule.setDatemiseencirculation(resultSet.getDate("datemiseencirculation"));
                vehicule.setEtat(resultSet.getString("etat"));
                vehicule.setNbkilometres(resultSet.getInt("nbkilometres"));
                vehicule.setPrixparjourdelocation(resultSet.getDouble("prixparjourdelocation"));

                vehicule.setMarque((Marque) marqueDao.findById(resultSet.getInt("idmarque")));
                vehicule.setModele((Modele) modeleDao.findById(resultSet.getInt("idmodele")));
                vehicule.setCategorie((Categorie) categorieDao.findById(resultSet.getInt("idcategorie")));
                vehicule.setType((Type) typeDao.findById(resultSet.getInt("idtype")));
                vehicule.setAgence((Agence) agenceDao.findById(resultSet.getInt("idagence")));

                vehicules.add(vehicule);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return vehicules;
    }

    @Override
    public Entity findById(int id) throws DaoException {
        return null;
    }

    public Entity findByImmatriculation(String immatriculation) throws DaoException {
        Vehicule vehicule = null;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM vehicule WHERE immatriculation = " + immatriculation);

            if(resultSet.next()){
                vehicule = new Vehicule();
                vehicule.setImmatriculation(resultSet.getString("immatriculation"));
                vehicule.setDatemiseencirculation(resultSet.getDate("datemiseencirculation"));
                vehicule.setEtat(resultSet.getString("etat"));
                vehicule.setNbkilometres(resultSet.getInt("nbkilometres"));
                vehicule.setPrixparjourdelocation(resultSet.getDouble("prixparjourdelocation"));

                vehicule.setMarque((Marque) marqueDao.findById(resultSet.getInt("idmarque")));
                vehicule.setModele((Modele) modeleDao.findById(resultSet.getInt("idmodele")));
                vehicule.setCategorie((Categorie) categorieDao.findById(resultSet.getInt("idcategorie")));
                vehicule.setType((Type) typeDao.findById(resultSet.getInt("idtype")));
                vehicule.setAgence((Agence) agenceDao.findById(resultSet.getInt("idagence")));

            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return vehicule;
    }

    @Override
    public void create(Entity entity) throws DaoException {

        Vehicule vehicule = (Vehicule) entity;

        PreparedStatement stmt= null;

        String sqlReq = "insert into vehicule(datemiseencirculation, etat, nbkilometres, prixparjourdelocation, idmarque, idmodele, idcategorie, idtype, idagence) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {

            stmt = connection.prepareStatement(sqlReq);

            stmt.setDate(1, vehicule.getDatemiseencirculation());
            stmt.setString(2, vehicule.getEtat());
            stmt.setInt(3, vehicule.getNbkilometres());
            stmt.setDouble(4, vehicule.getPrixparjourdelocation());

            stmt.setInt(5, vehicule.getMarque().getId());
            stmt.setInt(6, vehicule.getModele().getId());
            stmt.setInt(7, vehicule.getCategorie().getId());
            stmt.setInt(8, vehicule.getType().getId());
            stmt.setInt(9, vehicule.getAgence().getId());

            int res = stmt.executeUpdate();
            if (res > 0) {
                System.out.println("Ligne insérée");

                // Donne le bon id à l'instance d'entity
                if (Integer.parseInt(vehicule.getImmatriculation()) == 0) {
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT COUNT(immatriculation) AS m FROM vehicule");
                    if (resultSet.next()) {
                        int max = resultSet.getInt("m");
                        vehicule.setImmatriculation(max+"");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getLocalizedMessage());
        }
    }

    @Override
    public void update(Entity entity) throws DaoException {
        Vehicule vehicule = (Vehicule) entity;
        PreparedStatement stmt;
        String sqlReq = "UPDATE vehicule SET datemiseencirculation = ?, etat = ?, nbkilometres = ?, prixparjourdelocation = ?, idmarque = ?, idmodele = ?, idcategorie = ?, idtype = ?, idagence = ? WHERE immatriculation = ?";

        try {
            stmt = connection.prepareStatement(sqlReq);

            stmt.setDate(1, vehicule.getDatemiseencirculation());
            stmt.setString(2, vehicule.getEtat());
            stmt.setInt(3, vehicule.getNbkilometres());
            stmt.setDouble(4, vehicule.getPrixparjourdelocation());

            stmt.setInt(5, vehicule.getMarque().getId());
            stmt.setInt(6, vehicule.getModele().getId());
            stmt.setInt(7, vehicule.getCategorie().getId());
            stmt.setInt(8, vehicule.getType().getId());
            stmt.setInt(9, vehicule.getAgence().getId());

            stmt.setString(10, vehicule.getImmatriculation());

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

        Vehicule vehicule = (Vehicule) entity;

        Statement statement = null;
        try {
            statement = connection.createStatement();
            String sqlReq = "DELETE FROM vehicule WHERE immatriculation = "+vehicule.getImmatriculation();

            int res = statement.executeUpdate(sqlReq);
            if (res > 0) System.out.println("ligne supprimé");
            else System.out.println("vehicule introuvable");

        } catch(SQLException e) {
            System.err.println("Erreur SQL : " + e.getLocalizedMessage());
        }

    }

}