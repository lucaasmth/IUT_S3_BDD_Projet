package model;

public class Marque extends Entity {

    private String nom;

    public Marque() {
        this(0);
    }

    public Marque(int id) {
        this(id, null);
    }

    public Marque(String nom) {
        this(0, nom);
    }

    public Marque(int id, String nom) {
        super(id);
        this.nom = nom;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
