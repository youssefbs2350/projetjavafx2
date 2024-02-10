package detailsMatch;

public class Arbitre {
    private int idArbitre;
    private String nomArbitre;
    private int nbrMatchesArbitres;
    private int numeroTelephone;

    public Arbitre(int idArbitre, String nomArbitre, int nbrMatchesArbitres, int numeroTelephone) {
        this.idArbitre = idArbitre;
        this.nomArbitre = nomArbitre;
        this.nbrMatchesArbitres = nbrMatchesArbitres;
        this.numeroTelephone = numeroTelephone;
    }

    public int getIdArbitre() {
        return idArbitre;
    }

    public void setIdArbitre(int idArbitre) {
        this.idArbitre = idArbitre;
    }

    public String getNomArbitre() {
        return nomArbitre;
    }

    public void setNomArbitre(String nomArbitre) {
        this.nomArbitre = nomArbitre;
    }

    public int getNbrMatchesArbitres() {
        return nbrMatchesArbitres;
    }

    public void setNbrMatchesArbitres(int nbrMatchesArbitres) {
        this.nbrMatchesArbitres = nbrMatchesArbitres;
    }

    public int getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(int numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }
}
