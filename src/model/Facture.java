package model;//package model;

public class Facture extends Entity {

    private double montant;
    private Contrat contrat;

    public Facture() {
        this(0);
    }

    public Facture(int id) {
        this(id, 0, null);
    }

    public Facture(double montant, Contrat contrat) {
        this(0, montant, contrat);
    }

    public Facture(int id, double montant, Contrat contrat) {
        super(id);
        this.montant = montant;
        this.contrat = contrat;
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