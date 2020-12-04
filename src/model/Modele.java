package model;//package model;

public class Modele extends Entity {

    private int id;
    private String denomination;
    private int puissancefiscale;

    public Modele() {
        this(0);
    }

    public Modele(int id) {
        this(id, null, 0);
    }

    public Modele(String denomination, int puissancefiscale) {
        this.denomination = denomination;
        this.puissancefiscale = puissancefiscale;
    }

    public Modele(int id, String denomination, int puissancefiscale) {
        super();

        this.id = id;
        this.denomination = denomination;
        this.puissancefiscale = puissancefiscale;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getDenomination() {
        return denomination;
    }
    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public int getPuissancefiscale() {
        return puissancefiscale;
    }
    public void setPuissancefiscale(int puissancefiscale) {
        this.puissancefiscale = puissancefiscale;
    }
}