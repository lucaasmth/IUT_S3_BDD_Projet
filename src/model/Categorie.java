package model;//package model;

public class Categorie extends Entity {

    private String libelle;

    public Categorie() {
        this(0);
    }

    public Categorie(int id) {
        this(id, null);
    }

    public Categorie(String libelle) {
        this(0, libelle);
    }

    public Categorie(int id, String libelle) {
        super(id);
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}