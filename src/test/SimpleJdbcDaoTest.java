package test;//package test;

import dao.Dao;
import dao.jdbc.CategorieDaoImpl;
import dao.jdbc.FactureDaoImpl;
import dao.jdbc.TypeDaoImpl;
import dao.exception.DaoException;
import dao.jdbc.VehiculeDaoImpl;
import model.*;
import sql.PostgresConnection;

import java.sql.Connection;
import java.util.Collection;

public class SimpleJdbcDaoTest {

    private Connection connection;
    private Dao dao;

    /* TYPE */

    public void testFindAllTypes() {
        Dao dao = new TypeDaoImpl(connection);
        try {
            Collection<Entity> types = dao.findAll();
            for (Entity entity : types) {
                Type type = (Type) entity;

                log(type.getId() + " | " + type.getLibelle());
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    public void testFindType(int id) {
        try {
            Type type = (Type) dao.findById(id);
            log(type.getId() + " | " + type.getLibelle());
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    public void testCreateType(Type type) {
        try {
            dao.create(type);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    public void testUpdateType(Type type) {
        try {
            dao.update(type);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    public void testDeleteType(Type type) {
        try {
            dao.delete(type);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }




    /* CATEGORIE */

    public void testFindAllCategories() {
        Dao dao = new CategorieDaoImpl(connection);
        try {
            Collection<Entity> categories = dao.findAll();
            for (Entity entity : categories) {
                Categorie categorie = (Categorie) entity;

                log(categorie.getId() + " | " + categorie.getLibelle());
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    public void testFindCategorie(int id) {
        try {
            Categorie categorie = (Categorie) dao.findById(id);
            log(categorie.getId() + " | " + categorie.getLibelle());
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    public void testCreateCategorie(Categorie categorie) {
        try {
            dao.create(categorie);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    public void testUpdateCategorie(Categorie categorie) {
        try {
            dao.update(categorie);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    public void testDeleteCategorie(Categorie categorie) {
        try {
            dao.delete(categorie);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }





    /* MODELE */

    public void testFindAllModeles() {
        Dao dao = new ModeleDaoImpl(connection);
        try {
            Collection<Entity> modeles = dao.findAll();
            for (Entity entity : modeles) {
                Modele modele = (Modele) entity;

                log(modele.getId() + " | " + modele.getDenomination() + " | " + modele.getPuissancefiscale());
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    public void testFindModele(int id) {
        try {
            Modele modele = (Modele) dao.findById(id);
            log(modele.getId() + " | " + modele.getDenomination() + " | " + modele.getPuissancefiscale());
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    public void testCreateModele(Modele modele) {
        try {
            dao.create(modele);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    public void testUpdateModele(Modele modele) {
        try {
            dao.update(modele);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    public void testDeleteModele(Modele modele) {
        try {
            dao.delete(modele);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }



    /* FACTURE */

    public void testFindAllFactures() {
        Dao dao = new FactureDaoImpl(connection);
        try {
            Collection<Entity> factures = dao.findAll();
            for (Entity entity : factures) {
                Facture facture = (Facture) entity;

                log(facture.getId() + " | " + facture.getMontant() + " | " + facture.getContrat().getId());
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    public void testFindFacture(int id) {
        try {
            Facture facture = (Facture) dao.findById(id);
            log(facture.getId() + " | " + facture.getMontant() + " | " + facture.getContrat().getId());
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    public void testCreateFacture(Facture facture) {
        try {
            dao.create(facture);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    public void testUpdateFacture(Facture facture) {
        try {
            dao.update(facture);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    public void testDeleteFacture(Facture facture) {
        try {
            dao.delete(facture);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }







    /* VEHICULE */

    public void testFindAllVehicules() {
        Dao dao = new VehiculeDaoImpl(connection);
        try {
            Collection<Entity> vehicules = dao.findAll();
            for (Entity entity : vehicules) {
                Vehicule vehicule = (Vehicule) entity;

                log(vehicule.getImmatriculation() + " | " + vehicule.getDatemiseencirculation() + " | " + vehicule.getEtat() + " | " + vehicule.getNbkilometres() + " | " + vehicule.getPrixparjourdelocation() + " | " + vehicule.getMarque().getId() + " | " + vehicule.getModele().getId() + " | " + vehicule.getCategorie().getId() + " | " + vehicule.getType().getId() + " | " + vehicule.getAgence().getId());
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    public void testFindVehicule(int id) {
        try {
            Vehicule vehicule = (Vehicule) dao.findById(id);
            log(vehicule.getImmatriculation() + " | " + vehicule.getDatemiseencirculation() + " | " + vehicule.getEtat() + " | " + vehicule.getNbkilometres() + " | " + vehicule.getPrixparjourdelocation() + " | " + vehicule.getMarque().getId() + " | " + vehicule.getModele().getId() + " | " + vehicule.getCategorie().getId() + " | " + vehicule.getType().getId() + " | " + vehicule.getAgence().getId());
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    public void testCreateVehicule(Vehicule vehicule) {
        try {
            dao.create(vehicule);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    public void testUpdateVehicule(Vehicule vehicule) {
        try {
            dao.update(vehicule);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    public void testDeleteVehicule(Vehicule vehicule) {
        try {
            dao.delete(vehicule);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }







    public void test() {
        connection = PostgresConnection.getInstance();

        dao = new TypeDaoImpl(connection);

        log("cc");


    }

    public void log(String str){
        System.out.println(str);
    }

    public static void main(String[] args) {
        new SimpleJdbcDaoTest().test();
    }
}