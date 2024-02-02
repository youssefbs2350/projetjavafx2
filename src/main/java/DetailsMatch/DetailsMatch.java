package DetailsMatch;

import java.sql.Date;
import java.sql.Time;

public class DetailsMatch {
    private int idMatch;
    private String terrain;
    private Date dateMatch;
    private Time heure;
    private String ville;
    private int idArbitre;

    // Constructeurs, getters et setters
    public DetailsMatch(){};
    public DetailsMatch(int idMatch, String terrain, Date dateMatch, Time heure, String ville, int idArbitre) {
        this.idMatch = idMatch;
        this.terrain = terrain;
        this.dateMatch = dateMatch;
        this.heure = heure;
        this.ville = ville;
        this.idArbitre = idArbitre;
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

    public int getIdArbitre() {
        return idArbitre;
    }

    public void setIdArbitre(int idArbitre) {
        this.idArbitre = idArbitre;
    }

    // Vous pouvez ajouter d'autres méthodes ou fonctionnalités selon vos besoins
}