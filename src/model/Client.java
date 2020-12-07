package model;

public class Client extends Entity {
    private int id;
    private String nom;
    private String adresse;
    private int codePostal;
    private Ville ville;

    public Client() {

    }

    public Client(int id) {
        this.id = id;
    }

    public Client(String nom) {
        this.nom = nom;
    }

    public Client(int id, String nom, String adresse, int codePostal, Ville ville) {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
        this.codePostal = codePostal;
        this.ville = ville;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(int codePostal) {
        this.codePostal = codePostal;
    }

    public Ville getVille() {
        return ville;
    }

    public void setVille(Ville ville) {
        this.ville = ville;
    }
}
