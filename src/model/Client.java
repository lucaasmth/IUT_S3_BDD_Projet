package model;

public class Client extends Entity {

    private String nom;
    private String adresse;
    private int codePostal;
    private Ville ville;

    public Client() {
        this(0);
    }

    public Client(int id) {
        this(id, null, null, 0, null);
    }

    public Client(String nom) {
        this(0, nom, null, 0, null);
    }

    public Client(int id, String nom, String adresse, int codePostal, Ville ville) {
        super(id);
        this.nom = nom;
        this.adresse = adresse;
        this.codePostal = codePostal;
        this.ville = ville;
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
