package DetailsMatch;

import java.sql.Date;
import java.util.List;

public class DetailsMatchServiceImpl implements DetailsMatchService {
    private DetailsMatchDAO detailsMatchDAO;

    public DetailsMatchServiceImpl(DetailsMatchDAO detailsMatchDAO) {
        this.detailsMatchDAO = detailsMatchDAO;
    }

    @Override
    public void ajouterDetailsMatch(DetailsMatch detailsMatch) {
        detailsMatchDAO.ajouterDetailsMatch(detailsMatch);
    }

    @Override
    public void modifierDetailsMatch(DetailsMatch detailsMatch) {
        detailsMatchDAO.modifierDetailsMatch(detailsMatch);
    }

    @Override
    public void supprimerDetailsMatch(int idMatch) {
        detailsMatchDAO.supprimerDetailsMatch(idMatch);
    }

    @Override
    public DetailsMatch rechercherDetailsMatch(int idMatch) {
        return detailsMatchDAO.rechercherDetailsMatch(idMatch);
    }

    @Override
    public List<DetailsMatch> listerTousLesDetailsMatchs() {
        return detailsMatchDAO.listerTousLesDetailsMatchs();
    }


    @Override
    public List<DetailsMatch> rechercherParDate(Date date) {
        return detailsMatchDAO.rechercherParDate(date);
    }

    @Override
    public List<DetailsMatch> listerParVille(String ville) {
        return detailsMatchDAO.listerParVille(ville);
    }

    @Override
    public List<DetailsMatch> listerParArbitre(int idArbitre) {
        return detailsMatchDAO.listerParArbitre(idArbitre);
    }

    @Override
    public int nombreMatchesParTerrain(String terrain) {
        return detailsMatchDAO.nombreMatchesParTerrain(terrain);
    }
    // Implémentez d'autres méthodes avancées ici
}