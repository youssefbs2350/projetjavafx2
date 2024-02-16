package Championnat;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;

public class Ajoutchampionnat extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet championnatid = null;
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetjavafx", "root", "");
        statement = connection.createStatement();
        // Création des éléments de l'interface
        Label labelNomChampionnat = new Label("Nom du championnat :");
        TextField textFieldNomChampionnat = new TextField();
        Button boutonConfirmer = new Button("Confirmer");
        Button boutonReset = new Button("Réinitialiser");
        Button boutonFermer = new Button("Fermer");

        // Action lorsque le bouton "Confirmer" est cliqué
        boutonConfirmer.setOnAction(event -> {
            String nomChampionnat = textFieldNomChampionnat.getText();
            insererChampionnatDansLaBaseDeDonnees(nomChampionnat);
            System.out.println("Nom du championnat confirmé : " + nomChampionnat);
        });
        boutonFermer.setOnAction(event -> primaryStage.close());

        // Action lorsque le bouton "Réinitialiser" est cliqué
        boutonReset.setOnAction(event -> textFieldNomChampionnat.clear());

        // Création du conteneur pour organiser les éléments
        VBox root = new VBox();
        HBox buttonsBox = new HBox(boutonConfirmer, boutonReset);
        root.getChildren().addAll(labelNomChampionnat, textFieldNomChampionnat, buttonsBox , boutonFermer );

        // Création de la scène
        Scene scene = new Scene(root, 300, 200);

        // Configuration de la scène et affichage de la fenêtre
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ajouter un championnat");
        primaryStage.show();
    }
    private void insererChampionnatDansLaBaseDeDonnees(String nomChampionnat) {
        try {
            Connection connection = null;
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetjavafx", "root", "");
            String sql = "INSERT INTO championnat (championnatName) VALUES (?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nomChampionnat);
            statement.executeUpdate();
            System.out.println("Championnat inséré dans la base de données : " + nomChampionnat);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    }

