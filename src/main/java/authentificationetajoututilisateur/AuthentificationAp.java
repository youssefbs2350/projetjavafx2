package authentificationetajoututilisateur;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import javafx.geometry.Insets;

public class AuthentificationAp extends Application {

    private Authentification auth = Authentification.getInstance();

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

        // Appliquer des styles CSS aux boutons
        loginButton.getStyleClass().add("login-button");
        inscriptionButton.getStyleClass().add("inscription-button");

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
        layout.setPadding(new Insets(20));

        // Charger l'image de fond
        Image image = new Image("file:D://background.jpg");
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        layout.setBackground(background);
        Scene scene = new Scene(layout);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        primaryStage.setScene(scene);

        // Maximiser la fenêtre au démarrage
        primaryStage.setMaximized(true);

        primaryStage.show();
    }
    private void ouvrirNouvelleFenetre(String message) {
        Home home = new Home(message);
        Stage stage = new Stage();
        home.start(stage);
    }
}





