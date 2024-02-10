package detailsMatch;
import java.sql.Date;
import java.sql.Time;

public class DetailsMatch {
    private int idMatch;
    private String terrain;
    private Date dateMatch;
    private Time heure;
    private String ville;
    private Arbitre arbitre; // Represents the relationship with Arbitre class

    public DetailsMatch() {}

    public DetailsMatch(int idMatch, String terrain, Date dateMatch, Time heure, String ville, Arbitre arbitre) {
        this.idMatch = idMatch;
        this.terrain = terrain;
        this.dateMatch = dateMatch;
        this.heure = heure;
        this.ville = ville;
        this.arbitre = arbitre;
    }

    public int getIdMatch() {
        return idMatch;
    }

    public void setIdMatch(int idMatch) {
        this.idMatch = idMatch;
    }

    public String getTerrain() {
        return terrain;
    }

    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }

    public Date getDateMatch() {
        return dateMatch;
    }

    public void setDateMatch(Date dateMatch) {
        this.dateMatch = dateMatch;
    }

    public Time getHeure() {
        return heure;
    }

    public void setHeure(Time heure) {
        this.heure = heure;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public Arbitre getArbitre() {
        return arbitre;
    }

    public void setArbitre(Arbitre arbitre) {
        this.arbitre = arbitre;
    }

    // Other methods can be added as needed
}
