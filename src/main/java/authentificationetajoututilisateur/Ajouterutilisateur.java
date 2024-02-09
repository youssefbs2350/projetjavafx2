package authentificationetajoututilisateur;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Ajouterutilisateur extends Application {

    private Authentification auth = Authentification.getInstance();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Ajouter Utilisateur");
        primaryStage.setMaximized(true);

        Label usernameLabel = new Label("Nom d'utilisateur (au moins 5 caractères):");
        TextField usernameField = new TextField();

        Label nomLabel = new Label("Nom (au moins 5 caractères):");
        TextField nomField = new TextField();

        Label prenomLabel = new Label("Prénom (au moins 5 caractères):");
        TextField prenomField = new TextField();

        Label passwordLabel = new Label("Mot de passe (au moins 8 caractères avec au moins une majuscule, une minuscule et un chiffre):");
        PasswordField passwordField = new PasswordField();

        Button ajouterButton = new Button("S'inscrire");
        Label resultLabel = new Label();

        ajouterButton.setOnAction(e -> {
            String username = usernameField.getText();
            String nom = nomField.getText();
            String prenom = prenomField.getText();
            String password = passwordField.getText();

            String champManquant = controleSaisie(username, nom, prenom, password);
            if (champManquant == null) {
                boolean ajoutReussi = ajouterUtilisateur(username, nom, prenom, password);

                if (ajoutReussi) {
                    resultLabel.setText("Inscription réussie !");
                } else {
                    resultLabel.setText("Échec de l'inscription. Veuillez réessayer.");
                }
            } else {
                resultLabel.setText("Le champ '" + champManquant + "' est manquant ou le nom d'utilisateur est déjà utilisé.");
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(usernameLabel, usernameField, nomLabel, nomField, prenomLabel, prenomField, passwordLabel, passwordField, ajouterButton, resultLabel);

        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private String controleSaisie(String username, String nom, String prenom, String password) {
        if (username.isEmpty()) {
            return "Nom d'utilisateur";
        }

        if (username.length() < 5) {
            return "Nom d'utilisateur (au moins 5 caractères)";
        }

        if (nom.isEmpty()) {
            return "Nom";
        }

        if (nom.length() < 5) {
            return "Nom (au moins 5 caractères)";
        }

        if (prenom.isEmpty()) {
            return "Prénom";
        }

        if (prenom.length() < 5) {
            return "Prénom (au moins 5 caractères)";
        }

        if (password.isEmpty()) {
            return "Mot de passe";
        }

        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$")) {
            return "Mot de passe (au moins 8 caractères avec au moins une majuscule, une minuscule et un chiffre)";
        }

        try {
            Connection connection = auth.getConnection();
            String query = "SELECT username FROM utilisateurs WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return "Nom d'utilisateur déjà utilisé";
                }
            }
        } catch (SQLException exception) {
            System.err.println("Erreur SQL : " + exception.getMessage());
        }

        return null;
    }

    private boolean ajouterUtilisateur(String username, String nom, String prenom, String password) {
        try {
            Connection connection = auth.getConnection();
            String query = "INSERT INTO utilisateurs (username, nom, prenom, password, type) VALUES (?, ?, ?, ?, 'user')";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, nom);
                preparedStatement.setString(3, prenom);
                preparedStatement.setString(4, password);

                int lignesAffectees = preparedStatement.executeUpdate();

                return lignesAffectees > 0;
            }
        } catch (SQLException exception) {
            System.err.println("Erreur SQL : " + exception.getMessage());
            return false;
        }
    }
}