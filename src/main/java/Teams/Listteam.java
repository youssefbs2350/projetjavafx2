package Teams;
import authentificationetajoututilisateur.Home;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.sql.*;

public class Listteam implements Initializable {

    @FXML
    private TableView<String[]> table1;
    @FXML
    private Button addteam;
    @FXML
    private Button refreshButton;
    @FXML
    private Button viewPlayersButton ;

    @FXML
    private Text textmenu;
    private Stage primaryStage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Création des colonnes
        TableColumn<String[], String> column1 = new TableColumn<>("Nom de l'équipe      ");
        column1.setCellValueFactory(param -> {
            String[] rowData = param.getValue();
            return rowData != null && rowData.length > 0 ? new javafx.beans.property.SimpleStringProperty(rowData[0]) : null;
        });

        TableColumn<String[], String> column2 = new TableColumn<>("Nombre de joueurs      ");
        column2.setCellValueFactory(param -> {
            String[] rowData = param.getValue();
            return rowData != null && rowData.length > 1 ? new javafx.beans.property.SimpleStringProperty(rowData[1]) : null;
        });

        TableColumn<String[], String> column3 = new TableColumn<>("Nom du championnat       ");
        column3.setCellValueFactory(param -> {
            String[] rowData = param.getValue();
            return rowData != null && rowData.length > 2 ? new javafx.beans.property.SimpleStringProperty(rowData[2]) : null;
        });

        // Ajout des colonnes à la TableView
        table1.getColumns().addAll(column1, column2, column3);

        // Récupération des données de la base de données
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

            table1.getItems().addAll(data);

            table1.setRowFactory(tableView -> new TableRow<>() {
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


            loadData();

            textmenu.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    // Appel à la méthode pour charger la page d'accueil
                    loadHomePage();
                }
            });

            // Liaison de l'action du bouton de rafraîchissement avec la méthode refreshTable
            refreshButton.setOnAction(this::refreshTable);
            //pour voi la liste des joueurs
            viewPlayersButton.setOnAction(this::viewPlayers);

            addteam.setOnAction(event -> {
                Ajout_Team ajoutTeam = new Ajout_Team();
                ajoutTeam.start(new Stage());
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteRowFromTable(ActionEvent event) {
        // Récupérer l'indice de la ligne sélectionnée
        int selectedIndex = table1.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            // Récupérer les informations de l'équipe à partir de la TableView
            String teamName = table1.getItems().get(selectedIndex)[0]; // Suppose que le nom de l'équipe est dans la première colonne

            // Supprimer l'équipe de la TableView
            table1.getItems().remove(selectedIndex);

            // Supprimer l'équipe de la base de données
            deleteTeamFromDatabase(teamName);
        }
    }

    private void deleteTeamFromDatabase(String teamName) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetjavafx-3", "root", "")) {
            String sql = "DELETE FROM teams WHERE teamName = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, teamName);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Équipe supprimée de la base de données : " + teamName);
            } else {
                System.out.println("Équipe non trouvée dans la base de données : " + teamName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        // Effacer le contenu actuel de la TableView
        table1.getItems().clear();

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

            table1.getItems().addAll(data);

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
        int selectedIndex = table1.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            // Récupérer les informations de l'équipe à partir de la TableView
            String teamName = table1.getItems().get(selectedIndex)[0]; // Suppose que le nom de l'équipe est dans la première colonne

            // Ouvrir la fenêtre pour afficher la liste des joueurs pour cette équipe
            TeamPlayersList teamPlayersList = new TeamPlayersList(teamName);
            teamPlayersList.showPlayers();
        }
    }

    private void loadHomePage() {
        // Fermer la scène actuelle
        Stage currentStage = (Stage) textmenu.getScene().getWindow();
        currentStage.close();

        // Ouvrir la page d'accueil pour l'administrateur
        Home home = new Home("Bonjour", "Admin"); // Remplacez "Bonjour" par le message d'accueil approprié
        Stage newStage = new Stage();
        home.start(newStage);
    }
}
