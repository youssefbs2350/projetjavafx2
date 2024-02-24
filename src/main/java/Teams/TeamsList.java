package Teams;

import javafx.application.Application;
import javafx.collections.FXCollections;
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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TeamsList extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Connexion à la base de données
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetjavafx-3", "root", "");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM teams")) {

            Label emptyLabel1 = new Label(); // Ajout d'un label vide
            Label emptyLabel2 = new Label(); // Ajout d'un label vide
            Button ajouterEquipe = new Button("Ajouter une équipe");
            Label titleLabel = new Label("Les équipes");
            titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

            // TableView pour afficher les équipes
            TableView<String[]> tableView = new TableView<>();

            // Création des colonnes
            TableColumn<String[], String> column1 = new TableColumn<>("N°");
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

            // Ajout des colonnes à la TableView
            tableView.getColumns().addAll(column1, column2, column3);

            // Récupération des données de la base de données
            List<String[]> data = new ArrayList<>();
            while (resultSet.next()) {
                String[] row = new String[]{
                        resultSet.getString("teamId"),
                        resultSet.getString("teamName"),
                        resultSet.getString("nbrjoueurs")
                };
                data.add(row);
            }

            // Ajout des données à la TableView
            tableView.setItems(FXCollections.observableArrayList(data));

            VBox root = new VBox();
            root.setAlignment(Pos.CENTER);
            root.setSpacing(10);
            root.setPadding(new Insets(10));
            root.getChildren().addAll(emptyLabel1, emptyLabel2, titleLabel, tableView, ajouterEquipe);

            Scene scene = new Scene(root, 600, 400);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Liste des équipes");
            primaryStage.show();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
