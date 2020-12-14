package model;//package model;

public class Type extends Entity {

    private String libelle;

    public Type() {
        this(0);
    }
    public Type(int id) {
        this(id, null);
    }
    public Type(String libelle) {
        this(0, null);
    }
    public Type(int id, String libelle) {
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