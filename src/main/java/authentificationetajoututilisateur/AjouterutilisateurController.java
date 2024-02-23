package authentificationetajoututilisateur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AjouterutilisateurController implements Initializable {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label resultLabel;

    private Authentification authentification;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            authentification = Authentification.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleSubsButtonAction(ActionEvent event) {
        String username = usernameField.getText();
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String password = passwordField.getText();

        // Vérifier si les conditions sont respectées
        String errorMessage = verifierConditions(username, nom, prenom, password);
        if (!errorMessage.isEmpty()) {
            resultLabel.setText(errorMessage);
            return;
        }

        try {
            // Vérifier si le nom d'utilisateur existe déjà
            if (nomUtilisateurExiste(username)) {
                resultLabel.setText("Le nom d'utilisateur est déjà utilisé.");
                return;
            }

            // Utiliser le singleton d'authentification pour ajouter l'utilisateur à la base de données
            boolean ajoutSucces = ajouterUtilisateur(username, nom, prenom, password);

            if (ajoutSucces) {
                resultLabel.setText("Utilisateur ajouté avec succès !");
            } else {
                resultLabel.setText("Erreur lors de l'ajout de l'utilisateur.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception selon vos besoins
        }
    }

    // Méthode pour vérifier si le nom d'utilisateur existe déjà
    private boolean nomUtilisateurExiste(String username) throws SQLException {
        try (
                PreparedStatement preparedStatement = authentification.getConnection().prepareStatement("SELECT username FROM utilisateurs WHERE username = ?");
        ) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // Si une ligne est retournée, cela signifie que le nom d'utilisateur existe déjà
            }
        }
    }

    // Méthode pour ajouter un utilisateur à la base de données
    private boolean ajouterUtilisateur(String username, String nom, String prenom, String password) throws SQLException {
        try (
                PreparedStatement preparedStatement = authentification.getConnection().prepareStatement("INSERT INTO utilisateurs (username, nom, prenom, password,type) VALUES (?, ?, ?, ?,'user')");
        ) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, nom);
            preparedStatement.setString(3, prenom);
            preparedStatement.setString(4, password);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Si au moins une ligne est affectée, l'ajout a réussi
        }
    }

    // Méthode pour vérifier si les conditions sont respectées
    private String verifierConditions(String username, String nom, String prenom, String password) {
        StringBuilder errorMessage = new StringBuilder();

        if (username.length() < 5) {
            errorMessage.append("Le nom d'utilisateur doit contenir au moins 5 caractères.\n");
        }
        if (nom.length() < 5) {
            errorMessage.append("Le nom doit contenir au moins 5 caractères.\n");
        }
        if (prenom.length() < 5) {
            errorMessage.append("Le prénom doit contenir au moins 5 caractères.\n");
        }
        if (password.length() < 8) {
            errorMessage.append("Le mot de passe doit contenir au moins 8 caractères.\n");
        }
        if (!contientMajusculeEtMinuscule(password)) {
            errorMessage.append("Le mot de passe doit contenir au moins une majuscule et une minuscule.\n");
        }
        if (!contientChiffre(password)) {
            errorMessage.append("Le mot de passe doit contenir au moins un chiffre.\n");
        }

        return errorMessage.toString();
    }

    // Méthode pour vérifier si le mot de passe contient au moins une majuscule et une minuscule
    private boolean contientMajusculeEtMinuscule(String password) {
        boolean majuscule = false;
        boolean minuscule = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                majuscule = true;
            }
            if (Character.isLowerCase(c)) {
                minuscule = true;
            }
        }
        return majuscule && minuscule;
    }

    // Méthode pour vérifier si le mot de passe contient au moins un chiffre
    private boolean contientChiffre(String password) {
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }
}
