package DetailsMatch;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetailsMatchDAO {
    private Connection connection;

    public DetailsMatchDAO(Connection connection) {
        this.connection = connection;
    }

    public void ajouterDetailsMatch(DetailsMatch detailsMatch) {
        String query = "INSERT INTO detailsmatch (idmatch, terrain, datematch, heure, ville, idarbitre) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, detailsMatch.getIdMatch());
            preparedStatement.setString(2, detailsMatch.getTerrain());
            preparedStatement.setDate(3, detailsMatch.getDateMatch());
            preparedStatement.setTime(4, detailsMatch.getHeure());
            preparedStatement.setString(5, detailsMatch.getVille());
            preparedStatement.setInt(6, detailsMatch.getIdArbitre());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception selon vos besoins
        }
    }

    public void modifierDetailsMatch(DetailsMatch detailsMatch) {
        String query = "UPDATE detailsmatch SET terrain=?, datematch=?, heure=?, ville=?, idarbitre=? WHERE idmatch=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, detailsMatch.getTerrain());
            preparedStatement.setDate(2, detailsMatch.getDateMatch());
            preparedStatement.setTime(3, detailsMatch.getHeure());
            preparedStatement.setString(4, detailsMatch.getVille());
            preparedStatement.setInt(5, detailsMatch.getIdArbitre());
            preparedStatement.setInt(6, detailsMatch.getIdMatch());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception selon vos besoins
        }
    }

    public void supprimerDetailsMatch(int idMatch) {
        String query = "DELETE FROM detailsmatch WHERE idmatch=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idMatch);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception selon vos besoins
        }
    }

    public DetailsMatch rechercherDetailsMatch(int idMatch) {
        String query = "SELECT * FROM detailsmatch WHERE idmatch=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idMatch);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return creerDetailsMatchAPartirResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception selon vos besoins
        }
        return null;
    }

    public List<DetailsMatch> listerTousLesDetailsMatchs() {
        List<DetailsMatch> detailsMatches = new ArrayList<>();
        String query = "SELECT * FROM detailsmatch";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                DetailsMatch detailsMatch = creerDetailsMatchAPartirResultSet(resultSet);
                detailsMatches.add(detailsMatch);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception selon vos besoins
        }
        return detailsMatches;
    }

    public List<DetailsMatch> rechercherParDate(Date date) {
        List<DetailsMatch> detailsMatches = new ArrayList<>();
        String query = "SELECT * FROM detailsmatch WHERE datematch=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDate(1, date);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                DetailsMatch detailsMatch = creerDetailsMatchAPartirResultSet(resultSet);
                detailsMatches.add(detailsMatch);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception selon vos besoins
        }
        return detailsMatches;
    }

    public List<DetailsMatch> listerParVille(String ville) {
        List<DetailsMatch> detailsMatches = new ArrayList<>();
        String query = "SELECT * FROM detailsmatch WHERE ville=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, ville);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                DetailsMatch detailsMatch = creerDetailsMatchAPartirResultSet(resultSet);
                detailsMatches.add(detailsMatch);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception selon vos besoins
        }
        return detailsMatches;
    }

    public List<DetailsMatch> listerParArbitre(int idArbitre) {
        List<DetailsMatch> detailsMatches = new ArrayList<>();
        String query = "SELECT * FROM detailsmatch WHERE idarbitre=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idArbitre);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                DetailsMatch detailsMatch = creerDetailsMatchAPartirResultSet(resultSet);
                detailsMatches.add(detailsMatch);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception selon vos besoins
        }
        return detailsMatches;
    }

    public int nombreMatchesParTerrain(String terrain) {
        int nombreMatches = 0;
        String query = "SELECT COUNT(*) FROM detailsmatch WHERE terrain=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, terrain);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                nombreMatches = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception selon vos besoins
        }
        return nombreMatches;
    }

    private DetailsMatch creerDetailsMatchAPartirResultSet(ResultSet resultSet) throws SQLException {
        return new DetailsMatch(
                resultSet.getInt("idmatch"),
                resultSet.getString("terrain"),
                resultSet.getDate("datematch"),
                resultSet.getTime("heure"),
                resultSet.getString("ville"),
                resultSet.getInt("idarbitre")
        );
    }
    // Ajoutez d'autres méthodes pour les opérations de base et avancées
}