package Championnat;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Afficherequipes extends Application {
    // private String idChampionnat;
   // public Afficherequipes(String idChampionnat) {
  //      this.idChampionnat = idChampionnat; }
    private static final String DB_URL = "jdbc:mysql://localhost:3306/projetjavafx";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";




    @Override
    public void start(Stage primaryStage) {
        TableView<String[]> tableView = new TableView<>();

        // Créer les colonnes
        TableColumn<String[], String> column1 = new TableColumn<>("ID de l'équipe");
        column1.setCellValueFactory(param -> {
            String[] rowData = param.getValue();
            return rowData != null && rowData.length > 0 ? new javafx.beans.property.SimpleStringProperty(rowData[0]) : null;
        });

        TableColumn<String[], String> column2 = new TableColumn<>("Nom de l'équipe");
        column2.setCellValueFactory(param -> {
            String[] rowData = param.getValue();
            return rowData != null && rowData.length > 1 ? new javafx.beans.property.SimpleStringProperty(rowData[1]) : null;
        });

        TableColumn<String[], String> column3 = new TableColumn<>("Nombre de joueurs");
        column3.setCellValueFactory(param -> {
            String[] rowData = param.getValue();
            return rowData != null && rowData.length > 2 ? new javafx.beans.property.SimpleStringProperty(rowData[2]) : null;
        });

        TableColumn<String[], String> column4 = new TableColumn<>("Score de l'équipe");
        column4.setCellValueFactory(param -> {
            String[] rowData = param.getValue();
            return rowData != null && rowData.length > 3 ? new javafx.beans.property.SimpleStringProperty(rowData[3]) : null;
        });

        TableColumn<String[], String> column5 = new TableColumn<>("ID du championnat");
        column5.setCellValueFactory(param -> {
            String[] rowData = param.getValue();
            return rowData != null && rowData.length > 4 ? new javafx.beans.property.SimpleStringProperty(rowData[4]) : null;
        });

        tableView.getColumns().addAll(column1, column2, column3, column4, column5);

        // Récupérer les données de la base de données
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM teams WHERE championnatid = 1")) {

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

        VBox root = new VBox(tableView);
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Affichage des équipes");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
