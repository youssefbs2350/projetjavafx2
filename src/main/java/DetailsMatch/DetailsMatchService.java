package DetailsMatch;

import java.util.List;

public interface DetailsMatchService {
    void ajouterDetailsMatch(DetailsMatch detailsMatch);
    void modifierDetailsMatch(DetailsMatch detailsMatch);
    void supprimerDetailsMatch(int idMatch);
    DetailsMatch rechercherDetailsMatch(int idMatch);
    List<DetailsMatch> listerTousLesDetailsMatchs();

    // Méthodes avancées
    List<DetailsMatch> rechercherParDate(java.sql.Date date);
    List<DetailsMatch> listerParVille(String ville);
    List<DetailsMatch> listerParArbitre(int idArbitre);
    int nombreMatchesParTerrain(String terrain);
    // Ajoutez d'autres méthodes avancées selon vos besoins
}
