package authentificationetajoututilisateur;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.*;



public class AuthentificationAp extends Application {

    private Authentification auth = new Authentification();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Authentification JavaFX");

        Label usernameLabel = new Label("Nom d'utilisateur:");
        TextField usernameField = new TextField();

        Label passwordLabel = new Label("Mot de passe:");
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("Se connecter");
        Button inscriptionButton = new Button("Inscription");
        Label resultLabel = new Label();

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            boolean isAuthenticated = auth.authenticate(username, password);

            if (isAuthenticated) {
                // Connexion réussie, ouvrir une nouvelle fenêtre avec le message "Bonjour"
                ouvrirNouvelleFenetre("Bonjour " + username + " !");



                // Vous pouvez également fermer la fenêtre d'authentification si nécessaire
                primaryStage.close();

            } else {
                resultLabel.setText("Échec de la connexion. Vérifiez vos informations.");
            }
        });

        inscriptionButton.setOnAction(e -> {
            Ajouterutilisateur ajouterutilisateur = new Ajouterutilisateur();
            Stage stage = new Stage();
            ajouterutilisateur.start(stage);
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, loginButton, inscriptionButton, resultLabel);

        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void ouvrirNouvelleFenetre(String message) {

        Home Home = new Home (message);
        Stage stage = new Stage();
        Home.start(stage);
    }
    }


