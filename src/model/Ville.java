package model;

public class Ville extends Entity {

    private String nom;
    private int nombreHabitants;

    public Ville(){
        this(0);
    }

    public Ville(int id){
        this(id, null, 0);
    }

    public Ville(String nom){
        this(0, nom, 0);
    }

    public Ville(int id, String nom, int nombreHabitants){
        super(id);
        this.nom = nom;
        this.nombreHabitants = nombreHabitants;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNombreHabitants() {
        return this.nombreHabitants;
    }

    public void setNombreHabitants(int nombreHabitants) {
        this.nombreHabitants = nombreHabitants;
    }
}
