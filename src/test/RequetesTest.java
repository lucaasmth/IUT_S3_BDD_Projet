package test;

import dao.Dao;
import dao.exception.DaoException;
import dao.jdbc.*;
import model.*;
import sql.PostgresConnection;

import java.sql.Connection;
import java.util.*;

public class RequetesTest {

    private static Connection connection;
    private static Dao dao;
    private static Scanner scanner;

    public static void main(String[] args) {
        connection = PostgresConnection.getInstance();
        scanner = new Scanner(System.in);

        /*Dao vehiculeDao = new VehiculeDaoImpl(connection);
        Dao clientDao = new ClientDaoImpl(connection);
        Dao agenceDao = new AgenceDaoImpl(connection);
        Dao contratDao = new ContratDaoImpl(connection);

        try {
            Vehicule vehicule = (Vehicule) vehiculeDao.findById(1);
            Client client = (Client) clientDao.findById(1);
            Agence agence = (Agence) agenceDao.findById(1);

            Date today = new Date();
            int dureeJours = 5;

            Contrat contrat;

            contrat = locationVehicule(vehicule, client, today, dureeJours);
            finContrat(contrat, agence);

            contratDao.delete(contrat);

        } catch (DaoException e) {
            e.printStackTrace();
        }

        requete5();*/
        //requete6();
        //requete7();
        requete8();
        requete9();
        requete10();
    }


    /* 2 */
    public static Contrat locationVehicule(Vehicule vehicule, Client client, Date date, int dureeJours) {

        System.out.println(" -- Requête 2 -- ");
        Dao contratDao = new ContratDaoImpl(connection);

        Contrat contrat = new Contrat();

        contrat.setDateRetrait(date);

        Date dateRetour = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dateRetour);
        c.add(Calendar.DATE, dureeJours);
        dateRetour = c.getTime();

        contrat.setDateRetour(dateRetour);
        contrat.setKmRetrait(vehicule.getNbkilometres());
        contrat.setKmRetour(0);
        contrat.setVehicule(vehicule);
        contrat.setClient(client);
        contrat.setAgenceRetour(vehicule.getAgence());

        try {
            contratDao.create(contrat);
        } catch (DaoException e) {
            e.printStackTrace();
        }

        return contrat;
    }


    /* 3 */
    public static void finContrat(Contrat contrat, Agence agenceRetour) {

        System.out.println(" -- Requête 3 -- ");
        Dao contratDao = new ContratDaoImpl(connection);

        if (contrat.getAgenceRetour().equals(agenceRetour))
            return;

        contrat.setAgenceRetour(agenceRetour);

        try {
            contratDao.update(contrat);

            makeFacture(contrat);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }


    /* 4 */
    public static void makeFacture(Contrat contrat) {

        System.out.println(" -- Requête 4 -- ");
        Dao factureDao = new FactureDaoImpl(connection);

        Facture facture = new Facture();

        facture.setContrat(contrat);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrer le montant de la facture: ");
        int montant = scanner.nextInt();

        facture.setMontant(montant);

        try {
            factureDao.create(facture);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }


    // 5. Le chiffre d’affaire d’une agence donnée pour un mois donné.
    public static void requete5() {
        System.out.println(" -- Requête 5 -- ");
        Dao factureDao = new FactureDaoImpl(connection);


        Collection<Entity> entities = null;
        Collection<Facture> factures = new ArrayList<>();
        try {
            entities = factureDao.findAll();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        for (Entity facture : entities) factures.add((Facture) facture);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrer l'id de l'agence que vous voulez : ");
        int id = scanner.nextInt();

        int ca = 0;

        for (Facture facture : factures) {
            if (facture.getContrat().getAgenceRetour().getId() == id)
                ca += facture.getMontant();
        }

        System.out.print("Le chiffre d'affaire de l'agence " + id + " est de " + ca + " €");
    }


    public static void requete6() {
        System.out.println(" -- Requête 6 -- ");
        Dao vehiculeDao = new VehiculeDaoImpl(connection);
        Collection<Entity> entities = null;
        Collection<Vehicule> vehicules = new ArrayList<>();
        try {
            entities = vehiculeDao.findAll();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        for (Entity vehicule : entities) vehicules.add((Vehicule) vehicule);

        System.out.print("Entrer la marque à rechercher: ");
        String marque = scanner.next();
        for (Vehicule vehicule : vehicules) {
            if (vehicule.getMarque().getNom().equals(marque))
                System.out.println(vehicule.getModele().getDenomination());
        }
    }

    public static void requete7() {
        System.out.println(" -- Requête 7 -- ");
        Dao contratDao = new ContratDaoImpl(connection);
        Collection<Entity> entities = null;
        Collection<Contrat> contrats = new ArrayList<>();
        try {
            entities = contratDao.findAll();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        for (Entity contrat : entities) contrats.add((Contrat) contrat);

        System.out.print("Entrer l'ID de l'agence à rechercher: ");
        int agenceId = scanner.nextInt();
        System.out.print("Entrer l'année à rechercher: ");
        String annee = scanner.next();
        for (Contrat contrat : contrats) {
            if (contrat.getAgenceRetour().getId() == agenceId) {
                System.out.println(contrat.getClient().getNom());
            }
        }
        //TODO Améliorer
    }

    public static void requete8() {
        System.out.println(" -- Requête 8 -- ");
        Dao factureDao = new FactureDaoImpl(connection);
        Collection<Entity> entities = null;
        Collection<Facture> factures = new ArrayList<>();
        try {
            entities = factureDao.findAll();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        for (Entity facture : entities) factures.add((Facture) facture);

        Dao categorieDao = new CategorieDaoImpl(connection);
        Collection<Entity> categorieEntities = null;
        try {
            categorieEntities = categorieDao.findAll();
        } catch (DaoException e) {
            e.printStackTrace();
        }

        for (Entity categorie : categorieEntities) {
            double ca = 0;
            for (Facture facture : factures) {
                if (facture.getContrat().getVehicule().getCategorie().getId() == categorie.getId()) {
                    ca += facture.getMontant();
                }
            }
            System.out.println("Chiffre d'affaire pour " + ((Categorie) categorie).getLibelle() + " : " + ca);
        }
    }

    public static void requete9() {
        System.out.println(" -- Requête 9 -- ");
        Dao factureDao = new FactureDaoImpl(connection);
        Collection<Entity> entities = null;
        Collection<Facture> factures = new ArrayList<>();
        try {
            entities = factureDao.findAll();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        for (Entity facture : entities) factures.add((Facture) facture);

        Dao typeDao = new TypeDaoImpl(connection);
        Collection<Entity> typeEntities = null;
        try {
            typeEntities = typeDao.findAll();
        } catch (DaoException e) {
            e.printStackTrace();
        }

        for (Entity type : typeEntities) {
            double ca = 0;
            for (Facture facture : factures) {
                if (facture.getContrat().getVehicule().getType().getId() == type.getId()) {
                    ca += facture.getMontant();
                }
            }
            System.out.println("Chiffre d'affaire pour " + ((Type) type).getLibelle() + " : " + ca);
        }
    }

    public static void requete10() {
        System.out.println(" -- Requête 10 -- ");

        Dao vehiculeDao = new VehiculeDaoImpl(connection);
        Collection<Entity> entities = null;
        Collection<Vehicule> vehicules = new ArrayList<>();
        try {
            entities = vehiculeDao.findAll();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        for (Entity vehicule : entities) vehicules.add((Vehicule) vehicule);

        Dao agenceDao = new AgenceDaoImpl(connection);
        Collection<Entity> agencesEntities = null;
        Collection<Agence> agences = new ArrayList<>();
        try {
            agencesEntities = agenceDao.findAll();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        for (Entity agence : agencesEntities) agences.add((Agence) agence);

        for (Agence agence : agences) {
            int nb = 0;
            for (Vehicule vehicule : vehicules) {
                if (vehicule.getAgence().getId() == agence.getId() && vehicule.getNbkilometres() > 150000 && ((new Date().getTime() - vehicule.getDatemiseencirculation().getTime()) / 1000 / 60 / 60 / 24 / 365) > 2){
                    nb += 1;
                }
            }
            System.out.println("Nombre de véhicules de plus de 2 ans et plus de 150k KM pour l'agence " + (agence.getId() + " : " + nb));
        }
    }
}
