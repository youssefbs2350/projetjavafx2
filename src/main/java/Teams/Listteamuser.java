package Teams;

import authentificationetajoututilisateur.Authentification;
import authentificationetajoututilisateur.Home;
import authentificationetajoututilisateur.HomeUser;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.text.Text;

import javafx.scene.control.TableRow;




public class Listteamuser implements Initializable {

    @FXML
    private TableView<String[]> table2;

    @FXML
    private Button join;

    @FXML
    private Text teamlist1;

    @FXML
    private Text teamlist11;

    @FXML
    private Button refreshButton;

    @FXML
    private Button viewPlayersButton ;

    @FXML
    private Text textmenu;

    private String username ;
    private Authentification authentification;

    public String getUsername() {
        return username;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            authentification = Authentification.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Création des colonnes
        TableColumn<String[], String> column1 = new TableColumn<>("Team Name      ");
        column1.setCellValueFactory(param -> {
            String[] rowData = param.getValue();
            return rowData != null && rowData.length > 0 ? new javafx.beans.property.SimpleStringProperty(rowData[0]) : null;
        });

        TableColumn<String[], String> column2 = new TableColumn<>("Team Players       ");
        column2.setCellValueFactory(param -> {
            String[] rowData = param.getValue();
            return rowData != null && rowData.length > 1 ? new javafx.beans.property.SimpleStringProperty(rowData[1]) : null;
        });

        TableColumn<String[], String> column3 = new TableColumn<>("Championship Name        ");
        column3.setCellValueFactory(param -> {
            String[] rowData = param.getValue();
            return rowData != null && rowData.length > 2 ? new javafx.beans.property.SimpleStringProperty(rowData[2]) : null;
        });

        TableColumn<String[], String> column4 = new TableColumn<>("Statut          ");
        column4.setCellValueFactory(param -> {
            String[] rowData = param.getValue();
            if (rowData != null && rowData.length > 1) {
                int nbrJoueurs = Integer.parseInt(rowData[1]);
                return new javafx.beans.property.SimpleStringProperty(nbrJoueurs >= 6 ? "Full" : "incomplete");
            }
            return null;
        });

        // Ajout des colonnes à la TableView
        table2.getColumns().addAll(column1, column2, column3, column4);

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetjavafx-3", "root", "");
             PreparedStatement statement = connection.prepareStatement("SELECT teams.teamName, teams.nbrjoueurs, COUNT(Utilisateurs.teamid) AS nbreUtilisateurs, championnat.championnatName FROM teams JOIN championnat ON teams.championnatid = championnat.championnatId LEFT JOIN Utilisateurs ON teams.teamid = Utilisateurs.teamid GROUP BY teams.teamid");
             ResultSet resultSet = statement.executeQuery()) {

            List<String[]> data = new ArrayList<>();
            while (resultSet.next()) {
                String[] row = new String[]{
                        resultSet.getString("teamName"),
                        resultSet.getString("nbrjoueurs"),
                        resultSet.getString("championnatName")
                };
                data.add(row);
            }

            table2.getItems().addAll(data);
            table2.setRowFactory(tableView -> new TableRow<>() {
                @Override
                protected void updateItem(String[] item, boolean empty) {
                    super.updateItem(item, empty);
                    if (getIndex() % 2 == 0) {
                        // Apply green background to even rows
                        setStyle("-fx-background-color: #D4EED1;");
                    } else {
                        // Apply light green background to odd rows
                        setStyle("-fx-background-color: #C3F6BF;");
                    }
                }
            });

            // Liaison de l'action du bouton de rafraîchissement avec la méthode refreshTable
            refreshButton.setOnAction(this::refreshTable);

            loadData();


            //pour voi la liste des joueurs
            viewPlayersButton.setOnAction(this::viewPlayers);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Initialize user team info
        initializeUserTeamInfo();

        textmenu.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // Appel à la méthode pour charger la page d'accueil
                loadHomePage();
            }
        });
    }

    private void initializeUserTeamInfo() {
        // Get the username from authentification
        String username = authentification.getUsername1();
        // Display user team info
        displayUserTeamInfo(username);
    }

    @FXML
    private void actionjointeam(ActionEvent event) {
        int selectedIndex = table2.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            String teamName = table2.getItems().get(selectedIndex)[0];
            joinTeam(teamName);
        }
    }

    private String getTeamId(String teamName) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetjavafx-3", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT teamid FROM teams WHERE teamName = ?")) {
            preparedStatement.setString(1, teamName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("teamid");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void joinTeam(String teamName) {
        try {
            authentification = Authentification.getInstance();
            String username = authentification.getUsername1(); // Récupérer le nom d'utilisateur de l'utilisateur connecté
            String teamId = getTeamId(teamName);

                    joinTeamToDatabase(teamId, username);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isUserAlreadyInTeam(String username) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetjavafx-3", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT teamid FROM Utilisateurs WHERE username = ?")) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // Si l'utilisateur a déjà un teamid défini, il est déjà dans une équipe
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void joinTeamToDatabase(String teamId, String username) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetjavafx-3", "root", "")) {
            // Check if the user exists in the Utilisateurs table
            String selectSql = "SELECT * FROM utilisateurs WHERE username = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectSql);
            selectStatement.setString(1, username);
            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                // User exists, update the teamid
                String updateSql = "UPDATE utilisateurs SET teamid = ? WHERE username = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateSql);
                updateStatement.setString(1, teamId);
                updateStatement.setString(2, username);
                updateStatement.executeUpdate();
                System.out.println("Team: " + teamId + " updated for user: " + username);

                // Increment the number of players for the team
                incrementTeamPlayers(connection, teamId);

                // Check if teamId is not null and display the message window
                if (teamId != null) {
                    displayMessageWindow("Team joined successfully!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void incrementTeamPlayers(Connection connection, String teamId) throws SQLException {
        // Increment the number of players for the team
        String incrementSql = "UPDATE teams SET nbrjoueurs = nbrjoueurs + 1 WHERE teamid = ?";
        PreparedStatement incrementStatement = connection.prepareStatement(incrementSql);
        incrementStatement.setString(1, teamId);
        incrementStatement.executeUpdate();
        System.out.println("Number of players incremented for team: " + teamId);
    }

    private void displayMessageWindow(String message) {
        try {
            Stage stage = new Stage();

            // Charger le fichier FXML de l'interface d'inscription
            String fxmlDocPath = "D:\\Users\\youss\\.jdks\\jbr-17.0.8\\bin\\gitfx\\projetjavafx3\\src\\main\\java\\Teams\\MessageWindow.fxml";
            FXMLLoader loader = new FXMLLoader(new File(fxmlDocPath).toURI().toURL());
            AnchorPane root = loader.load();
            Scene scene = new Scene(root, 612, 293);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Authentification JavaFX");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayUserTeamInfo(String username) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetjavafx-3", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT teams.teamName, teams.scoreteam FROM Utilisateurs JOIN teams ON utilisateurs.teamid = teams.teamid WHERE utilisateurs.username = ?")) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String teamName = resultSet.getString("teamName");
                    int scoreteam = resultSet.getInt("scoreteam");
                    // Set the retrieved team name and score to the Text objects in your FXML
                    teamlist1.setText("TeamName: " + teamName);
                    teamlist11.setText("TeamScore: " + String.valueOf(scoreteam));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        // Effacer le contenu actuel de la TableView
        table2.getItems().clear();

        // Charger les données depuis la base de données
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetjavafx-3", "root", "");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT teams.teamName, teams.nbrjoueurs, championnat.championnatName FROM teams JOIN championnat ON teams.championnatid = championnat.championnatId")) {

            List<String[]> data = new ArrayList<>();
            while (resultSet.next()) {
                String[] row = new String[]{
                        resultSet.getString("teamName"),
                        resultSet.getString("nbrjoueurs"),
                        resultSet.getString("championnatName")
                };
                data.add(row);
            }

            table2.getItems().addAll(data);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void refreshTable(ActionEvent event) {
        // Rafraîchir les données dans la TableView
        loadData();
    }

    @FXML
    private void viewPlayers(ActionEvent event) {
        // Récupérer l'indice de la ligne sélectionnée
        int selectedIndex = table2.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            // Récupérer les informations de l'équipe à partir de la TableView
            String teamName = table2.getItems().get(selectedIndex)[0]; // Suppose que le nom de l'équipe est dans la première colonne

            // Ouvrir la fenêtre pour afficher la liste des joueurs pour cette équipe
            TeamPlayersList teamPlayersList = new TeamPlayersList(teamName);
            teamPlayersList.showPlayers();
        }
    }

    private void loadHomePage() {
        String username = getUsername();
        // Fermer la scène actuelle
        Stage currentStage = (Stage) teamlist1.getScene().getWindow();
        currentStage.close();
        // Code pour charger la page d'accueil, par exemple :
        HomeUser home = new HomeUser("Bonjour", "Nom d'utilisateur"); // Remplacez "Message d'accueil" et "Nom d'utilisateur" par les valeurs appropriées
        Stage newStage = new Stage();
        home.start(newStage);
    }


}
