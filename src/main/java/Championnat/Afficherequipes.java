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
        // If you need to initialize anything, you can do it here
    }

    public Afficherequipes(String idChampionnat, String nomchampionnat) {
        this.nomchampionnat = nomchampionnat;
        this.idChampionnat = idChampionnat;
    }

    @Override
    public void start(Stage primaryStage) {
        Label titleLabel = new Label(nomchampionnat);
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        Label Text = new Label("Championnat : ");
        Text.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        HBox hbox = new HBox();
        hbox.getChildren().add(Text);
        HBox.setMargin(Text,new Insets(0, 0, 0, 20));























        TableView<String[]> tableView = new TableView<>();
        // Créer les colonnes
        TableColumn<String[], String> column2 = new TableColumn<>("Nom de l'équipe");
        column2.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        column2.setPrefWidth(180);
        column2.setCellValueFactory(param -> {
            String[] rowData = param.getValue();
            return rowData != null && rowData.length > 1 ? new javafx.beans.property.SimpleStringProperty(rowData[1]) : null;
        });
        TableColumn<String[], String> column3 = new TableColumn<>("Nombre de joueurs");
        column3.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        column3.setPrefWidth(180);
        column3.setCellValueFactory(param -> {
            String[] rowData = param.getValue();
            return rowData != null && rowData.length > 2 ? new javafx.beans.property.SimpleStringProperty(rowData[2]) : null;
        });
        TableColumn<String[], String> column4 = new TableColumn<>("Score de l'équipe");
        column4.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        column4.setPrefWidth(180);
        column4.setCellValueFactory(param -> {
            String[] rowData = param.getValue();
            return rowData != null && rowData.length > 3 ? new javafx.beans.property.SimpleStringProperty(rowData[3]) : null;
        });
        TableColumn<String[], String> column5 = new TableColumn<>("ID du championnat");
        column5.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        column5.setPrefWidth(180);
        column5.setCellValueFactory(param -> {
            String[] rowData = param.getValue();
            return rowData != null && rowData.length > 4 ? new javafx.beans.property.SimpleStringProperty(rowData[4]) : null;
        });
        tableView.getColumns().addAll(column2, column3, column4, column5);
        String query = "SELECT * FROM teams WHERE championnatid = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            // Remplacer le paramètre dans la requête SQL avec idChampionnat
            statement.setString(1, idChampionnat);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tableView.getItems().add(new String[]{
                        resultSet.getString("teamId"),
                        resultSet.getString("teamName"),
                        resultSet.getString("nbrjoueurs"),
                        resultSet.getString("scoreteam"),
                        resultSet.getString("championnatid")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Button closeButton = new Button("Fermer");
        closeButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");
        closeButton.setOnAction(event -> primaryStage.close());
        VBox.setMargin(closeButton, new Insets(0, 0, 10, 550));
        VBox root = new VBox(Text,titleLabel,tableView,closeButton);
        root.setSpacing(10);
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
