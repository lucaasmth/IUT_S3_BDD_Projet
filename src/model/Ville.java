package model;

public class Ville extends Entity {
    private int id;
    private String nom;
    private int nombreHabitants;

    public Ville(){

    }

    public Ville(int id){
        this.id = id;
    }

    public Ville(String nom){
        this.nom = nom;
    }

    public Ville(int id, String nom, int nombreHabitants){
        this.id = id;
        this.nom = nom;
        this.nombreHabitants = nombreHabitants;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
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
