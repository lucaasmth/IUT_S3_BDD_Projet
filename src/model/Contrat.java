package model;

import java.util.Date;

public class Contrat extends Entity {

    private Date dateRetrait;
    private Date dateRetour;
    private int kmRetrait;
    private int kmRetour;
    private Client client;
    private Vehicule vehicule;
    private Agence agenceRetour;

    public Contrat() {
        this(0, null, null, 0, 0, null, null, null);
    }

    public Contrat(int id, Date dateRetrait, Date dateRetour, int kmRetrait, int kmRetour, Client client, Vehicule vehicule, Agence agenceRetour) {
        super(id);
        this.dateRetrait = dateRetrait;
        this.dateRetour = dateRetour;
        this.kmRetrait = kmRetrait;
        this.kmRetour = kmRetour;
        this.client = client;
        this.vehicule = vehicule;
        this.agenceRetour = agenceRetour;
    }

    public Date getDateRetrait() {
        return dateRetrait;
    }

    public void setDateRetrait(Date dateRetrait) {
        this.dateRetrait = dateRetrait;
    }

    public Date getDateRetour() {
        return dateRetour;
    }

    public void setDateRetour(Date dateRetour) {
        this.dateRetour = dateRetour;
    }

    public int getKmRetrait() {
        return kmRetrait;
    }

    public void setKmRetrait(int kmRetrait) {
        this.kmRetrait = kmRetrait;
    }

    public int getKmRetour() {
        return kmRetour;
    }

    public void setKmRetour(int kmRetour) {
        this.kmRetour = kmRetour;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

    public Agence getAgenceRetour() {
        return agenceRetour;
    }

    public void setAgenceRetour(Agence agenceRetour) {
        this.agenceRetour = agenceRetour;
    }
}
