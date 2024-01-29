package authentificationetajoututilisateur;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
        Button inscriptionButton = new Button("Inscription"); // Ajout du bouton Inscription
        Label resultLabel = new Label();

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            boolean isAuthenticated = auth.authenticate(username, password);

            if (isAuthenticated) {
                resultLabel.setText("Connexion réussie !");
            } else {
                resultLabel.setText("Échec de la connexion. Vérifiez vos informations.");
            }
        });

        inscriptionButton.setOnAction(e -> {
            // Crée une nouvelle instance de la classe Ajouterutilisateur et appelle sa méthode start
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
}
