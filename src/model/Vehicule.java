package model;//package model;

import java.sql.Date;

public class Vehicule extends Entity {

    private String immatriculation;
    private Date datemiseencirculation;
    private String etat;
    private int nbkilometres;
    private double prixparjourdelocation;
    private Marque marque;
    private Modele modele;
    private Categorie categorie;
    private Type type;
    private Agence agence;

    public Vehicule() {
        this("0");
    }

    public Vehicule(String immatriculation) {
        this(immatriculation, null, 0, 0, null, null, null, null, null);
    }

    public Vehicule(String immatriculation, Date datemiseencirculation,
                    int nbkilometres, double prixparjourdelocation, Marque marque,
                    Modele modele, Categorie categorie, Type type, Agence agence) {

        super(0);
        this.immatriculation = immatriculation;
        this.datemiseencirculation = datemiseencirculation;
        this.etat = etat;
        this.nbkilometres = nbkilometres;
        this.prixparjourdelocation = prixparjourdelocation;
        this.marque = marque;
        this.modele = modele;
        this.categorie = categorie;
        this.type = type;
        this.agence = agence;
    }

    public String getImmatriculation() {
        return immatriculation;
    }
    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public Date getDatemiseencirculation() {
        return datemiseencirculation;
    }
    public void setDatemiseencirculation(Date datemiseencirculation) {
        this.datemiseencirculation = datemiseencirculation;
    }

    public String getEtat() {
        return etat;
    }
    public void setEtat(String etat) {
        this.etat = etat;
    }

    public int getNbkilometres() {
        return nbkilometres;
    }
    public void setNbkilometres(int nbkilometres) {
        this.nbkilometres = nbkilometres;
    }

    public double getPrixparjourdelocation() {
        return prixparjourdelocation;
    }
    public void setPrixparjourdelocation(double prixparjourdelocation) {
        this.prixparjourdelocation = prixparjourdelocation;
    }

    public Marque getMarque() {
        return marque;
    }
    public void setMarque(Marque marque) {
        this.marque = marque;
    }

    public Modele getModele() {
        return modele;
    }
    public void setModele(Modele modele) {
        this.modele = modele;
    }

    public Categorie getCategorie() {
        return categorie;
    }
    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }

    public Agence getAgence() {
        return agence;
    }
    public void setAgence(Agence agence) {
        this.agence = agence;
    }
}