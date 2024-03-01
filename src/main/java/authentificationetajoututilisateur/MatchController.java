package authentificationetajoututilisateur;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

public class MatchController {
    @FXML
    private TableView<Match> matchTableView;

    @FXML
    private TableView<Match> matchResultsTableView;

    @FXML
    private TableColumn<Match, Integer> teamAScoreColumn;

    @FXML
    private TableColumn<Match, Integer> teamBScoreColumn;

    @FXML
    private TextField searchField;

    private MatchDAO matchDAO;

    public MatchController() throws SQLException {
        this.matchDAO = new MatchDAO(Authentification.getInstance());
    }

    @FXML
    private void initialize() {
        loadMatchData();
    }

    @FXML
    private void loadMatchData() {
        try {
            System.out.println("Loading match data...");
            List<Match> matches = matchDAO.getAllMatches();
            ObservableList<Match> matchObservableList = FXCollections.observableArrayList(matches);
            matchTableView.setItems(matchObservableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    /*@FXML
    private void handleAddMatch() {
        // Create a dialog for adding a match
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Match");
        dialog.setHeaderText(null);
        dialog.setContentText("Please enter match details:");

        // Add text fields for match attributes
        TextField teamAIdField = new TextField();
        teamAIdField.setPromptText("Team A ID");
        TextField teamBIdField = new TextField();
        teamBIdField.setPromptText("Team B ID");
        TextField fieldField = new TextField();
        fieldField.setPromptText("Field");
        TextField dateMatchField = new TextField();
        dateMatchField.setPromptText("Date (YYYY-MM-DD)"); // Updated prompt text
        TextField cityField = new TextField();
        cityField.setPromptText("City");
        TextField refereeIdField = new TextField();
        refereeIdField.setPromptText("Referee ID");

        // Set the dialog content
        dialog.getDialogPane().setContent(new VBox(8, teamAIdField, teamBIdField, fieldField, dateMatchField, cityField, refereeIdField));

        // Show the dialog and wait for user input
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            // Create a new match object with user input
            Match newMatch = new Match();
            newMatch.setTeam_A_id(Integer.parseInt(teamAIdField.getText()));
            newMatch.setTeam_B_id(Integer.parseInt(teamBIdField.getText()));
            newMatch.setField(fieldField.getText());

            // Validate and parse the date
            LocalDate date;
            try {
                date = LocalDate.parse(dateMatchField.getText(), DateTimeFormatter.ISO_DATE);
            } catch (DateTimeParseException e) {
                e.printStackTrace();
                showAlert("Invalid date format. Please enter date in YYYY-MM-DD format.");
                return; // Exit method if date format is invalid
            }
            newMatch.setDate_match(Date.valueOf(date));

            newMatch.setCity(cityField.getText());
            newMatch.setReferee_id(Integer.parseInt(refereeIdField.getText()));

            // Try to add the match to the database
            try {
                matchDAO.addMatch(newMatch);
                matchTableView.getItems().add(newMatch);
            } catch (SQLException e) {
                e.printStackTrace();
                // Display an error message if adding the match fails
                showAlert("Failed to add match. Please try again.");
            }
        }
    }
*/
    // Helper method to show an alert
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    private void handleAddMatch() {
        // Create a dialog for adding a match
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Match");
        dialog.setHeaderText(null);
        dialog.setContentText("Please enter match details:");

        // Add text fields for match attributes
        TextField teamAIdField = new TextField();
        teamAIdField.setPromptText("Team A ID");
        TextField teamBIdField = new TextField();
        teamBIdField.setPromptText("Team B ID");
        TextField teamAScoreField = new TextField();
        teamAScoreField.setPromptText("Team A Score");
        TextField teamBScoreField = new TextField();
        teamBScoreField.setPromptText("Team B Score");
        TextField fieldField = new TextField();
        fieldField.setPromptText("Field");
        TextField dateMatchField = new TextField();
        dateMatchField.setPromptText("Date (YYYY-MM-DD)"); // Updated prompt text
        TextField cityField = new TextField();
        cityField.setPromptText("City");
        TextField refereeIdField = new TextField();
        refereeIdField.setPromptText("Referee ID");

        // Set the dialog content
        dialog.getDialogPane().setContent(new VBox(8, teamAIdField, teamBIdField, teamAScoreField, teamBScoreField, fieldField, dateMatchField, cityField, refereeIdField));

        // Show the dialog and wait for user input
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            // Create a new match object with user input
            Match newMatch = new Match();
            newMatch.setTeam_A_id(Integer.parseInt(teamAIdField.getText()));
            newMatch.setTeam_B_id(Integer.parseInt(teamBIdField.getText()));
            newMatch.setTeam_A_score(Integer.parseInt(teamAScoreField.getText()));
            newMatch.setTeam_B_score(Integer.parseInt(teamBScoreField.getText()));
            newMatch.setField(fieldField.getText());

            // Validate and parse the date
            LocalDate date;
            try {
                date = LocalDate.parse(dateMatchField.getText(), DateTimeFormatter.ISO_DATE);
            } catch (DateTimeParseException e) {
                e.printStackTrace();
                showAlert("Invalid date format. Please enter date in YYYY-MM-DD format.");
                return; // Exit method if date format is invalid
            }
            newMatch.setDate_match(Date.valueOf(date));

            newMatch.setCity(cityField.getText());
            newMatch.setReferee_id(Integer.parseInt(refereeIdField.getText()));

            // Try to add the match to the database
            try {
                matchDAO.addMatch(newMatch);
                matchTableView.getItems().add(newMatch);
            } catch (SQLException e) {
                e.printStackTrace();
                // Display an error message if adding the match fails
                showAlert("Failed to add match. Please try again.");
            }
        }
    }



    @FXML
    private void handleDeleteMatch() {
        Match selectedMatch = matchTableView.getSelectionModel().getSelectedItem();
        if (selectedMatch != null) {
            try {
                matchDAO.deleteMatch(selectedMatch.getMatch_id());
                matchTableView.getItems().remove(selectedMatch);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleUpdateMatch() {
        // Get the selected match from the table view
        Match selectedMatch = matchTableView.getSelectionModel().getSelectedItem();
        if (selectedMatch == null) {
            showAlert("Please select a match to update.");
            return;
        }

        // Create a dialog for updating the match
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Update Match");
        dialog.setHeaderText(null);
        dialog.setContentText("Please update match details:");

        // Add text fields for match attributes
        TextField teamAIdField = new TextField(String.valueOf(selectedMatch.getTeam_A_id()));
        TextField teamBIdField = new TextField(String.valueOf(selectedMatch.getTeam_B_id()));
        TextField teamAScoreField = new TextField(String.valueOf(selectedMatch.getTeam_A_score()));
        TextField teamBScoreField = new TextField(String.valueOf(selectedMatch.getTeam_B_score()));
        TextField fieldField = new TextField(selectedMatch.getField());
        TextField dateMatchField = new TextField(selectedMatch.getDate_match().toString());
        TextField cityField = new TextField(selectedMatch.getCity());
        TextField refereeIdField = new TextField(String.valueOf(selectedMatch.getReferee_id()));

        // Set the dialog content
        dialog.getDialogPane().setContent(new VBox(8, teamAIdField, teamBIdField, teamAScoreField, teamBScoreField, fieldField, dateMatchField, cityField, refereeIdField));

        // Show the dialog and wait for user input
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            // Update the selected match with user input
            selectedMatch.setTeam_A_id(Integer.parseInt(teamAIdField.getText()));
            selectedMatch.setTeam_B_id(Integer.parseInt(teamBIdField.getText()));
            selectedMatch.setTeam_A_score(Integer.parseInt(teamAScoreField.getText()));
            selectedMatch.setTeam_B_score(Integer.parseInt(teamBScoreField.getText()));
            selectedMatch.setField(fieldField.getText());

            // Validate and parse the date
            LocalDate date;
            try {
                date = LocalDate.parse(dateMatchField.getText(), DateTimeFormatter.ISO_DATE);
            } catch (DateTimeParseException e) {
                e.printStackTrace();
                showAlert("Invalid date format. Please enter date in YYYY-MM-DD format.");
                return; // Exit method if date format is invalid
            }
            selectedMatch.setDate_match(Date.valueOf(date));

            selectedMatch.setCity(cityField.getText());
            selectedMatch.setReferee_id(Integer.parseInt(refereeIdField.getText()));

            // Try to update the match in the database
            try {
                matchDAO.updateMatch(selectedMatch);
                // Update the match in the table view
                matchTableView.refresh();
                System.out.println("Match updated successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
                // Display an error message if updating the match fails
                showAlert("Failed to update match. Please try again.");
            }
        }
    }

    @FXML
    private void handleSearch() {
        String searchTerm = searchField.getText();
        if (!searchTerm.isEmpty()) {
            try {
                List<Match> matches = matchDAO.searchMatches(searchTerm);
                matchTableView.getItems().clear();
                matchTableView.getItems().addAll(matches);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            loadMatchData();
        }
    }

    @FXML
    private void handleGetMatchesForTeam() {
        // Implement logic to get matches for a specific team
        // Example:
        
        int teamId = 1; // Example team ID
        try {
            List<Match> matches = matchDAO.getMatchesForTeam(teamId);
            matchTableView.getItems().clear();
            matchTableView.getItems().addAll(matches);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    @FXML
    private void handleGetMatchesInCity() {
        // Implement logic to get matches in a specific city
        // Example:
        
        String city = "Example City"; // Example city
        try {
            List<Match> matches = matchDAO.getMatchesInCity(city);
            matchTableView.getItems().clear();
            matchTableView.getItems().addAll(matches);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    @FXML
    private void handleGetMatchesAfterDate() {
        // Implement logic to get matches after a specific date
        // Example:
        
        Date date = Date.valueOf("2024-01-01"); // Example date
        try {
            List<Match> matches = matchDAO.getMatchesAfterDate(date);
            matchTableView.getItems().clear();
            matchTableView.getItems().addAll(matches);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    @FXML
    private void handleGetMatchesByReferee() {
        // Implement logic to get matches by a specific referee
        // Example:
        
        int refereeId = 1; // Example referee ID
        try {
            List<Match> matches = matchDAO.getMatchesByReferee(refereeId);
            matchTableView.getItems().clear();
            matchTableView.getItems().addAll(matches);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    @FXML
    private void handleGetMatchesByScore() {
        // Implement logic to get matches within a score range
        // Example:
        
        int minScore = 0; // Example minimum score
        int maxScore = 3; // Example maximum score
        try {
            List<Match> matches = matchDAO.getMatchesByScore(minScore, maxScore);
            matchTableView.getItems().clear();
            matchTableView.getItems().addAll(matches);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
}
