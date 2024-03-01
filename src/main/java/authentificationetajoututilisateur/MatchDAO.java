package authentificationetajoututilisateur;


import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MatchDAO implements Initializable {
    private Authentification authentification;

    public MatchDAO(Authentification authentification) {
        this.authentification = authentification;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            authentification = Authentification.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Match> getAllMatches() throws SQLException {
        List<Match> matches = new ArrayList<>();
        String query = "SELECT * FROM `match`";
        try (PreparedStatement statement = authentification.getConnection().prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            System.out.println("selecting...");
            while (resultSet.next()) {
                Match match = extractMatchFromResultSet(resultSet);
                matches.add(match);
                System.out.println(matches);
            }
        }
        return matches;
    }


    public void addMatch(Match match) throws SQLException {
        // Debugging: Print match object properties
        System.out.println("Team A ID: " + match.getTeam_A_id());
        System.out.println("Team B ID: " + match.getTeam_B_id());
        System.out.println("Field: " + match.getField());
        System.out.println("Date: " + match.getDate_match());
        System.out.println("City: " + match.getCity());
        System.out.println("Referee ID: " + match.getReferee_id());
        System.out.println("Team A Score: " + match.getTeam_A_score());
        System.out.println("Team B Score: " + match.getTeam_B_score());

        String query = "INSERT INTO `match` (team_A_id, team_B_id, team_A_score, team_B_score, field, date_match, city, referee_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = authentification.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, match.getTeam_A_id());
            statement.setInt(2, match.getTeam_B_id());
            statement.setInt(3, match.getTeam_A_score());
            statement.setInt(4, match.getTeam_B_score());
            statement.setString(5, match.getField());
            statement.setDate(6, match.getDate_match());
            statement.setString(7, match.getCity());
            statement.setInt(8, match.getReferee_id());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int matchId = generatedKeys.getInt(1);
                        match.setMatch_id(matchId);
                    }
                }
            }
        }
        // Debugging: Print success message
        System.out.println("Match added successfully.");
    }

    public void deleteMatch(int matchId) throws SQLException {
        String query = "DELETE FROM `match` WHERE match_id = ?";
        try (PreparedStatement statement = authentification.getConnection().prepareStatement(query)) {
            statement.setInt(1, matchId);
            statement.executeUpdate();
        }
    }

    public void updateMatch(Match match) throws SQLException {
        String query = "UPDATE `match` SET team_A_id=?, team_B_id=?, team_A_score=?, team_B_score=?, field=?, date_match=?, city=?, referee_id=? WHERE match_id=?";
        try (PreparedStatement statement = authentification.getConnection().prepareStatement(query)) {
            statement.setInt(1, match.getTeam_A_id());
            statement.setInt(2, match.getTeam_B_id());
            statement.setInt(3, match.getTeam_A_score());
            statement.setInt(4, match.getTeam_B_score());
            statement.setString(5, match.getField());
            statement.setDate(6, match.getDate_match());
            statement.setString(7, match.getCity());
            statement.setInt(8, match.getReferee_id());
            statement.setInt(9, match.getMatch_id());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Match updated successfully.");
            }
        }
    }

    public List<Match> searchMatches(String searchTerm) throws SQLException {
        List<Match> matches = new ArrayList<>();
        String query = "SELECT * FROM `match` WHERE field LIKE ? OR city LIKE ?";
        try (PreparedStatement statement = authentification.getConnection().prepareStatement(query)) {
            statement.setString(1, "%" + searchTerm + "%");
            statement.setString(2, "%" + searchTerm + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Match match = extractMatchFromResultSet(resultSet);
                matches.add(match);
            }
        }
        return matches;
    }

    private Match extractMatchFromResultSet(ResultSet resultSet) throws SQLException {
        Match match = new Match();
        match.setMatch_id(resultSet.getInt("match_id"));
        match.setTeam_A_id(resultSet.getInt("team_A_id"));
        match.setTeam_B_id(resultSet.getInt("team_B_id"));
        match.setTeam_A_score(resultSet.getInt("team_A_score"));
        match.setTeam_B_score(resultSet.getInt("team_B_score"));
        match.setField(resultSet.getString("field"));
        match.setDate_match(resultSet.getDate("date_match"));
        match.setCity(resultSet.getString("city"));
        match.setReferee_id(resultSet.getInt("referee_id"));
        return match;
    }
    public List<Match> getMatchesForTeam(int teamId) throws SQLException {
        List<Match> matches = new ArrayList<>();
        String query = "SELECT * FROM `match` WHERE team_A_id = ? OR team_B_id = ?";
        try (PreparedStatement statement = authentification.getConnection().prepareStatement(query)) {
            statement.setInt(1, teamId);
            statement.setInt(2, teamId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Match match = extractMatchFromResultSet(resultSet);
                matches.add(match);
            }
        }
        return matches;
    }

    public List<Match> getMatchesInCity(String city) throws SQLException {
        List<Match> matches = new ArrayList<>();
        String query = "SELECT * FROM `match` WHERE city = ?";
        try (PreparedStatement statement = authentification.getConnection().prepareStatement(query)) {
            statement.setString(1, city);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Match match = extractMatchFromResultSet(resultSet);
                matches.add(match);
            }
        }
        return matches;
    }

    public List<Match> getMatchesAfterDate(Date date) throws SQLException {
        List<Match> matches = new ArrayList<>();
        String query = "SELECT * FROM `match` WHERE date_match > ?";
        try (PreparedStatement statement = authentification.getConnection().prepareStatement(query)) {
            statement.setDate(1, date);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Match match = extractMatchFromResultSet(resultSet);
                matches.add(match);
            }
        }
        return matches;
    }

    public List<Match> getMatchesByReferee(int refereeId) throws SQLException {
        List<Match> matches = new ArrayList<>();
        String query = "SELECT * FROM `match` WHERE referee_id = ?";
        try (PreparedStatement statement = authentification.getConnection().prepareStatement(query)) {
            statement.setInt(1, refereeId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Match match = extractMatchFromResultSet(resultSet);
                matches.add(match);
            }
        }
        return matches;
    }

    public List<Match> getMatchesByScore(int minScore, int maxScore) throws SQLException {
        List<Match> matches = new ArrayList<>();
        String query = "SELECT * FROM `match` WHERE team_A_score BETWEEN ? AND ? OR team_B_score BETWEEN ? AND ?";
        try (PreparedStatement statement = authentification.getConnection().prepareStatement(query)) {
            statement.setInt(1, minScore);
            statement.setInt(2, maxScore);
            statement.setInt(3, minScore);
            statement.setInt(4, maxScore);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Match match = extractMatchFromResultSet(resultSet);
                matches.add(match);
            }
        }
        return matches;
    }
    
}
