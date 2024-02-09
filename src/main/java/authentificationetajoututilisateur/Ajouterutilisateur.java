package authentificationetajoututilisateur;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
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

        Label usernameLabel = new Label("Nom d'utilisateur:");
        TextField usernameField = new TextField();

        Label nomLabel = new Label("Nom:");
        TextField nomField = new TextField();

        Label prenomLabel = new Label("Prénom:");
        TextField prenomField = new TextField();

        Label passwordLabel = new Label("Mot de passe:");
        PasswordField passwordField = new PasswordField();

        Button ajouterButton = new Button("S'inscrire");
        Label resultLabel = new Label();

        ajouterButton.setOnAction(e -> {
            String username = usernameField.getText();
            String nom = nomField.getText();
            String prenom = prenomField.getText();
            String password = passwordField.getText();

            boolean ajoutReussi = ajouterUtilisateur(username, nom, prenom, password);

            if (ajoutReussi) {
                resultLabel.setText("Inscription réussie !");
            } else {
                resultLabel.setText("Échec de l'inscription. Veuillez réessayer.");
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(usernameLabel, usernameField, nomLabel, nomField, prenomLabel, prenomField, passwordLabel, passwordField, ajouterButton, resultLabel);

        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
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




