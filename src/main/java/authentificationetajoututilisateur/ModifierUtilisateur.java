package authentificationetajoututilisateur;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ModifierUtilisateur extends Application {
    private Connection connection;

    public ModifierUtilisateur() {
        // Récupérer la connexion à la base de données à partir du singleton Authentification
        connection = Authentification.getInstance().getConnection();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Modifier Utilisateur");

        Label titleLabel = new Label("Modifier Utilisateur");
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setFont(Font.font("Arial", 24));

        Label usernameLabel = new Label("Nom d'utilisateur:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Entrez le nom d'utilisateur");

        Label prenomLabel = new Label("Prénom:");
        TextField prenomField = new TextField();
        prenomField.setPromptText("Entrez le prénom");

        Label nomLabel = new Label("Nom:");
        TextField nomField = new TextField();
        nomField.setPromptText("Entrez le nom");

        Label passwordLabel = new Label("Mot de passe:");
        TextField passwordField = new TextField();
        passwordField.setPromptText("Entrez le mot de passe");

        Button modifierButton = new Button("Modifier");
        modifierButton.setOnAction(e -> {
            String username = usernameField.getText();
            String prenom = prenomField.getText();
            String nom = nomField.getText();
            String password = passwordField.getText();

            if (validerSaisie(username, prenom, nom, password)) {
                if (modifierInformationsUtilisateur(username, prenom, nom, password)) {
                    System.out.println("Informations de l'utilisateur modifiées avec succès.");
                    // Vous pouvez ajouter ici des actions supplémentaires après la modification
                } else {
                    System.err.println("Échec de la modification des informations de l'utilisateur.");
                }
            } else {
                System.err.println("Veuillez remplir tous les champs correctement.");
            }
        });

        VBox layout = new VBox(20);
        layout.getChildren().addAll(titleLabel, usernameLabel, usernameField, prenomLabel, prenomField, nomLabel, nomField, passwordLabel, passwordField, modifierButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(40));
        layout.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(layout, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private boolean validerSaisie(String username, String prenom, String nom, String password) {
        return !username.isEmpty() && !prenom.isEmpty() && !nom.isEmpty() && !password.isEmpty();
    }

    private boolean modifierInformationsUtilisateur(String username, String prenom, String nom, String password) {
        try {
            // Préparer la requête de mise à jour
            String query = "UPDATE utilisateurs SET prenom = ?, nom = ?, password = ? WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, prenom);
            preparedStatement.setString(2, nom);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, username);

            // Exécuter la requête de mise à jour
            int rowsAffected = preparedStatement.executeUpdate();

            // Vérifier si la mise à jour a réussi
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
