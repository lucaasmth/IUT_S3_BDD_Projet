package model;//package model;

public class Modele extends Entity {

    private String denomination;
    private int puissancefiscale;

    public Modele() {
        this(0);
    }

    public Modele(int id) {
        this(id, null, 0);
    }

    public Modele(String denomination, int puissancefiscale) {
        this(0, denomination, puissancefiscale);
    }

    public Modele(int id, String denomination, int puissancefiscale) {
        super(id);
        this.denomination = denomination;
        this.puissancefiscale = puissancefiscale;
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