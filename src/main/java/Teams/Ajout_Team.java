
package Teams;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Ajout_Team extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Connexion à la base de données
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetjavafx-3", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Création des éléments de l'interface
        Label titleLabel = new Label("Ajout d'une équipe");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        Label labelNomEquipe = new Label("Nom de l'équipe :");
        labelNomEquipe.setStyle("-fx-font-weight: bold;"); // Mise en gras du texte
        TextField textFieldNomEquipe = new TextField();

        // Créer le ComboBox des championnats
        VBox championnatSelector = ChampionnatSelector.createChampionnatSelector("jdbc:mysql://localhost:3306/projetjavafx-3", "root", "");
        Label labelChampionnat = new Label("Nom du championnat :");
        labelChampionnat.setStyle("-fx-font-weight: bold;"); // Mise en gras du texte

        Button boutonConfirmer = new Button("Confirmer");
        boutonConfirmer.setOnAction(event -> {
            String teamName = textFieldNomEquipe.getText();

            insererEquipeDansLaBaseDeDonnees(teamName   );
            System.out.println("Équipe créée : " + teamName);
        });

        Button retourEquipe = new Button("Liste des équipes");


        boutonConfirmer.setStyle("-fx-background-color: #7DBC22; -fx-text-fill: white; -fx-font-weight: bold;");
        retourEquipe.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");

        VBox root = new VBox();
        HBox titleBox = new HBox(titleLabel); // Créer une HBox pour le titre
        titleBox.setStyle("-fx-alignment: center;"); // Centrer le titre horizontalement

        HBox buttonsBox = new HBox(boutonConfirmer, retourEquipe);
        VBox.setMargin(buttonsBox, new Insets(0, 0, 10, 30));
        buttonsBox.setAlignment(Pos.CENTER_LEFT);
        buttonsBox.setSpacing(400); // Espacement entre les boutons

        root.getChildren().addAll(titleBox, labelNomEquipe, textFieldNomEquipe, labelChampionnat, championnatSelector, buttonsBox);
        Scene scene = new Scene(root, 720, 600);

        // Configuration de la scène et affichage de la fenêtre
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ajouter une équipe");
        primaryStage.show();
    }

    private void insererEquipeDansLaBaseDeDonnees(String teamName) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetjavafx-3", "root", "")) {
            String sql = "INSERT INTO Teams (teamName, int a) VALUES (?, 1)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, teamName);
           // statement.setInt(2, championnatid); // Spécifie le championnatid
            statement.executeUpdate();
            System.out.println("Équipe insérée dans la base de données : " + teamName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

