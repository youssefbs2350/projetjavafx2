/*package authentificationetajoututilisateur;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AuthentificationApController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label resultLabel;

    private Authentification auth = Authentification.getInstance();

    @FXML
    public void initialize() {
        // Vous pouvez initialiser des éléments ici si nécessaire
    }

    @FXML
    public void handleLoginButton() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        boolean isAuthenticated = auth.authenticate(username, password);

        if (isAuthenticated) {
            // Connexion réussie, ouvrir une nouvelle fenêtre avec le message "Bonjour"
            resultLabel.setText("Bonjour " + username + " !");
        } else {
            resultLabel.setText("Échec de la connexion. Vérifiez vos informations.");
        }
    }

    @FXML
    public void handleInscriptionButton() {
        // Code pour afficher la vue d'inscription
    }
}*/
package authentificationetajoututilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.*;
import javafx.scene.layout.AnchorPane;


public class AuthentificationApController implements Initializable {

    FXMLLoader loader2 = new FXMLLoader();
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label resultLabel;
    private Connection connection;

    public AuthentificationApController() throws SQLException {
        connection = Authentification.getInstance().getConnection();
    }

    @FXML
    private void handleLoginButtonAction(ActionEvent event) throws SQLException {
        String username = usernameField.getText();
        String password = passwordField.getText();


        // Vérifier les informations d'identification en utilisant le singleton Authentification
        if (Authentification.getInstance().authenticate(username, password)) {
            // Les informations d'identification sont correctes
            resultLabel.setText("Connexion réussie !");

            String message = "  Bonjour " + username + " !";
            System.out.println("username "+username);
            if(getUserType(username).equals("admin"))
                ouvrirHomeStage(message, username);
            else
                ouvrirUserHomeStage(message, username);

        } else {
            // Nom d'utilisateur ou mot de passe incorrect
            resultLabel.setText("Nom d'utilisateur ou mot de passe incorrect !");
        }
    }
    private String getUserType(String username) throws SQLException {
        // Créer la requête SQL pour obtenir le type d'utilisateur en fonction du nom d'utilisateur
        String query = "SELECT type FROM utilisateurs WHERE username = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            // Remplacer le paramètre dans la requête par le nom d'utilisateur fourni
            pstmt.setString(1, username);

            try (ResultSet resultSet = pstmt.executeQuery()) {
                // Si l'utilisateur est trouvé, retourner le type d'utilisateur
                if (resultSet.next()) {
                    return resultSet.getString("type");
                } else {
                    // Si l'utilisateur n'est pas trouvé, retourner une valeur par défaut ou lever une exception
                    throw new SQLException("Utilisateur introuvable: " + username);
                }
            }
        }
    }

    @FXML
    private void handleInscriptionButtonAction(ActionEvent event) {
        try {
            String fxmlDocPath = "D:\\Users\\youss\\.jdks\\jbr-17.0.8\\bin\\gitfx\\projetjavafx3\\src\\main\\java\\authentificationetajoututilisateur\\Ajouterutilisateur.fxml";
            FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
            AnchorPane root = (AnchorPane) loader2.load(fxmlStream);
            // Créer une nouvelle scène avec le contenu du fichier FXML chargé
            Scene scene = new Scene(root);

            // Créer un nouveau stage pour le fichier Ajouterutilisateur.fxml
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Inscription JavaFX");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer les erreurs de chargement du fichier FXML
        }
    }

    // Méthode pour ouvrir un nouveau stage avec la classe Home
    private void ouvrirHomeStage(String message , String username) {

        // Charger le fichier FXML Home.fxml



        Home home = new Home(message ,username);
        Stage stage = new Stage();
        home.start(stage);

    }

    private void ouvrirUserHomeStage(String message , String username) {

        // Charger le fichier FXML Home.fxml



        HomeUser home = new HomeUser(message ,username);
        Stage stage = new Stage();
        home.start(stage);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


}