package model;

public class Agence extends Entity {

    private int nbEmployes;
    private Ville ville;

    public Agence() {
        this(0);
    }

    public Agence(int id) {
        this(id, 0, null);
    }

    public Agence(int id, int nbEmployes, Ville ville){
        super(id);
        this.nbEmployes = nbEmployes;
        this.ville = ville;
    }

    public int getNbEmployes() {
        return nbEmployes;
    }

    public void setNbEmployes(int nbEmployes) {
        this.nbEmployes = nbEmployes;
    }

    public Ville getVille() {
        return ville;
    }

    public void setVille(Ville ville) {
        this.ville = ville;
    }
}
