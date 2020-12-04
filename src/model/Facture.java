package model;//package model;

public class Facture extends Entity {

    private int id;
    private double montant;
    private Contrat contrat;

    public Facture() {
        this(0);
    }

    public Facture(int id) {
        this(id, 0, null);
    }

    public Facture(String denomination, double montant, Contrat contrat) {
        this.montant = montant;
        this.contrat = contrat;
    }

    public Facture(int id, double montant, Contrat contrat) {
        super();

        this.id = id;
        this.montant = montant;
        this.contrat = contrat;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public double getMontant() {
        return montant;
    }
    public void setMontant(double montant) {
        this.montant = montant;
    }

    public Contrat getContrat() { return contrat; }
    public void setContrat(Contrat contrat) {
        this.contrat = contrat;
    }
}