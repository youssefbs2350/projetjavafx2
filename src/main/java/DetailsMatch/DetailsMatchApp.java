package DetailsMatch;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class DetailsMatchApp extends Application {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/projetjavafx";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private Connection connection;
    private DetailsMatchService detailsMatchService;

    public DetailsMatchApp() {
    }

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        initUI(primaryStage);
    }

    private void initUI(Stage primaryStage) {
        primaryStage.setTitle("Application de Détails de Match");

        // Création de composants d'interface utilisateur
        TableView<DetailsMatch> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        refreshTableView(tableView);

        Button addButton = new Button("Ajouter");
        Button deleteButton = new Button("Supprimer");
        TextField villeField = new TextField();
        Button searchButton = new Button("Rechercher par Ville");
        Label resultLabel = new Label();

        // Ajout des gestionnaires d'événements
        addButton.setOnAction(e -> {
            // Logique pour ajouter un détail de match
            // Appelez detailsMatchService.ajouterDetailsMatch() avec les données appropriées
            refreshTableView(tableView);
        });

        deleteButton.setOnAction(e -> {
            // Logique pour supprimer un détail de match
            // Appelez detailsMatchService.supprimerDetailsMatch() avec l'ID approprié
            refreshTableView(tableView);
        });

        searchButton.setOnAction(e -> {
            // Logique pour rechercher par ville
            // Appelez detailsMatchService.listerParVille() avec la ville appropriée
            String ville = villeField.getText();
            List<DetailsMatch> result = detailsMatchService.listerParVille(ville);
            resultLabel.setText("Résultat de la recherche : " + result.toString());
        });

        // Mise en page de l'interface utilisateur
        VBox vbox = new VBox(tableView, addButton, deleteButton, villeField, searchButton, resultLabel);
        Scene scene = new Scene(vbox, 600, 400);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void refreshTableView(TableView<DetailsMatch> tableView) {
        // Logique pour rafraîchir les données dans le TableView
        // Appelez detailsMatchService.listerTousLesDetailsMatchs() et mettez à jour le TableView
        List<DetailsMatch> detailsMatches = detailsMatchService.listerTousLesDetailsMatchs();
        tableView.getItems().setAll(detailsMatches);
    }

    @Override
    public void stop() {
        // Logique pour fermer la connexion lorsque l'application se termine
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}