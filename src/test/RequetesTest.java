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

    public static void main(String[] args) {
        connection = PostgresConnection.getInstance();

        Dao vehiculeDao = new VehiculeDaoImpl(connection);
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

        requete5();
        requete6();
    }



    /* 2 */
    public static Contrat locationVehicule(Vehicule vehicule, Client client, Date date, int dureeJours){

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
    public static void finContrat(Contrat contrat, Agence agenceRetour){

        System.out.println(" -- Requête 3 -- ");
        Dao contratDao = new ContratDaoImpl(connection);

        if(contrat.getAgenceRetour().equals(agenceRetour))
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
    public static void makeFacture(Contrat contrat){

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
                ca+= facture.getMontant();
        }

        System.out.print("Le chiffre d'affaire de l'agence "+id+" est de "+ca+" €");
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

        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrer la marque à rechercher: ");
        String marque = scanner.next();
        for (Vehicule vehicule : vehicules) {
            if (vehicule.getMarque().getNom().equals(marque))
                System.out.println(vehicule.getModele().getDenomination());
        }
    }
}
