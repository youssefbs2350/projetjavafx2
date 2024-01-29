package authentificationetajoututilisateur;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Ajouterutilisateur extends Application {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/projetjavafx";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Ajouter Utilisateur");

        Label nomLabel = new Label("Nom:");
        TextField nomField = new TextField();

        Label passwordLabel = new Label("Mot de passe:");
        PasswordField passwordField = new PasswordField();

        Button ajouterButton = new Button("S'inscrire");
        Label resultLabel = new Label();

        ajouterButton.setOnAction(e -> {
            String nom = nomField.getText();
            String password = passwordField.getText();

            boolean ajoutReussi = ajouterUtilisateur(nom, password);

            if (ajoutReussi) {
                resultLabel.setText("Inscription réussie !");
            } else {
                resultLabel.setText("Échec de l'inscription. Veuillez réessayer.");
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(nomLabel, nomField, passwordLabel, passwordField, ajouterButton, resultLabel);

        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private boolean ajouterUtilisateur(String nom, String password) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO utilisateurs (username, password, type) VALUES (?, ?, 'user')";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, nom);
                preparedStatement.setString(2, password);

                int lignesAffectees = preparedStatement.executeUpdate();

                return lignesAffectees > 0;
            }
        } catch (SQLException exception) {
            System.err.println("Erreur SQL : " + exception.getMessage());
            return false;
        }
    }
}


