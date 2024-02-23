package Championnat;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.*;
public class Afficherequipes extends Application {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/projetjavafx";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private String nomchampionnat;
    private String idChampionnat;
    public Afficherequipes() {
    }
    public Afficherequipes(String idChampionnat, String nomchampionnat) {
        this.nomchampionnat = nomchampionnat;
        this.idChampionnat = idChampionnat;
    }
    @Override
    public void start(Stage primaryStage) {
        Label titleLabel = new Label("List des Equipes dans la Chammpionnat : "+nomchampionnat);
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        TableView<String[]> tableView = new TableView<>();
        TableColumn<String[], String> column2 = new TableColumn<>("Nom de l'équipe");
        column2.setPrefWidth(180);
        column2.setCellValueFactory(param -> {
            String[] rowData = param.getValue();
            return rowData != null && rowData.length > 1 ? new javafx.beans.property.SimpleStringProperty(rowData[1]) : null;
        });
        TableColumn<String[], String> column3 = new TableColumn<>("Nombre de joueurs");
        column3.setPrefWidth(180);
        column3.setCellValueFactory(param -> {
            String[] rowData = param.getValue();
            return rowData != null && rowData.length > 2 ? new javafx.beans.property.SimpleStringProperty(rowData[2]) : null;
        });
        TableColumn<String[], String> column4 = new TableColumn<>("Score de l'équipe");
        column4.setPrefWidth(180);
        column4.setCellValueFactory(param -> {
            String[] rowData = param.getValue();
            return rowData != null && rowData.length > 3 ? new javafx.beans.property.SimpleStringProperty(rowData[3]) : null;
        });
        TableColumn<String[], String> column5 = new TableColumn<>("ID du championnat");
        column5.setPrefWidth(180);
        column5.setCellValueFactory(param -> {
            String[] rowData = param.getValue();
            return rowData != null && rowData.length > 4 ? new javafx.beans.property.SimpleStringProperty(rowData[4]) : null;
        });
        tableView.getColumns().addAll(column2, column3, column4, column5);
        String query = "SELECT * FROM team WHERE championship_id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            // Remplacer le paramètre dans la requête SQL avec idChampionnat
            statement.setString(1, idChampionnat);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tableView.getItems().add(new String[]{
                        resultSet.getString("team_id"),
                        resultSet.getString("team_name"),
                        resultSet.getString("number_of_players"),
                        resultSet.getString("team_score"),
                        resultSet.getString("championship_id")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Button closeButton = new Button("Fermer");
        Button retour = new Button("Championnat List");
        retour.setOnAction(event -> {
            primaryStage.close();
            Championnat retourpage = new Championnat();
            try {
                retourpage.start(new Stage());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        closeButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");
        retour.setStyle("-fx-background-color: #7DBC22; -fx-text-fill: white; -fx-font-weight: bold;");
        closeButton.setOnAction(event -> primaryStage.close());
        HBox buttonBox = new HBox(closeButton, retour);
        buttonBox.setAlignment(Pos.CENTER_LEFT);
        buttonBox.setSpacing(320);
        VBox.setMargin(buttonBox, new Insets(0, 0, 10, 50));
        VBox root = new VBox(titleLabel,tableView,buttonBox);
        root.setSpacing(30);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 720, 450);
        primaryStage.setTitle("Affichage des équipes");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
