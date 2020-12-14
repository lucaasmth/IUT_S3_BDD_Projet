package test;

import dao.Dao;
import dao.exception.DaoException;
import dao.jdbc.VehiculeDaoImpl;
import model.Entity;
import model.Vehicule;
import sql.PostgresConnection;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class RequetesTest {

    private static Connection connection;
    private static Dao dao;

    public static void main(String[] args) {
        connection = PostgresConnection.getInstance();
        requete6();
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
