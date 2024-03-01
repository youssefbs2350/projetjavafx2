package Teams;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;

import java.sql.*;
import java.util.Map;

public class Ajout_Team extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Add Team");

        // Création des éléments de l'interface
        Label titleLabel = new Label("Add Team");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        Label labelNomEquipe = new Label("Team Name :");
        labelNomEquipe.setStyle("-fx-font-weight: bold;"); // Mise en gras du texte
        TextField textFieldNomEquipe = new TextField();

        // Créer le ComboBox des championnats
        VBox championnatSelector = ChampionnatSelector.createChampionnatSelector("jdbc:mysql://localhost:3306/projetjavafx-3", "root", "");
        Label labelChampionnat = new Label("Championship Name :");
        labelChampionnat.setStyle("-fx-font-weight: bold;"); // Mise en gras du texte

        Button boutonConfirmer = new Button("Confirm");
        Button boutonQuitter = new Button("Quitter");

        boutonConfirmer.setStyle("-fx-background-color: #2254F5; -fx-text-fill: white; -fx-font-weight: bold;");
        boutonQuitter.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");

        VBox root = new VBox(10);
        root.setBackground(new Background(new BackgroundFill(Color.web("#8FCA83"), CornerRadii.EMPTY, Insets.EMPTY)));
        root.getChildren().addAll(titleLabel, labelNomEquipe, textFieldNomEquipe, labelChampionnat, championnatSelector, boutonConfirmer, boutonQuitter);
        root.setAlignment(Pos.CENTER_LEFT);
        root.setPadding(new Insets(10));
        Scene scene = new Scene(root, 300, 250);

        // Configuration de la scène et affichage de la fenêtre
        primaryStage.setScene(scene);
        primaryStage.show();

        boutonConfirmer.setOnAction(event -> {
            String teamName = textFieldNomEquipe.getText();
            int championnatId = getChampionnatId(championnatSelector);
            insererEquipeDansLaBaseDeDonnees(teamName, championnatId);
            System.out.println("Équipe créée : " + teamName);
        });

        boutonQuitter.setOnAction(event -> {
            primaryStage.close();
        });
    }

    private int getChampionnatId(VBox championnatSelector) {
        ComboBox<Map.Entry<Integer, String>> comboBoxChampionnat = findComboBox(championnatSelector);
        if (comboBoxChampionnat != null && comboBoxChampionnat.getValue() != null) {
            String championnatName = comboBoxChampionnat.getValue().getValue();

            // Écrire la logique pour récupérer l'identifiant du championnat à partir de son nom
            int championnatId = -1; // Valeur par défaut si l'identifiant n'est pas trouvé

            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetjavafx-3", "root", "")) {
                String sql = "SELECT championnatid FROM championnat WHERE championnatName = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, championnatName);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    championnatId = resultSet.getInt("championnatid");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return championnatId;
        }

        return -1;
    }

    private ComboBox<Map.Entry<Integer, String>> findComboBox(VBox container) {
        for (int i = 0; i < container.getChildren().size(); i++) {
            if (container.getChildren().get(i) instanceof ComboBox) {
                return (ComboBox<Map.Entry<Integer, String>>) container.getChildren().get(i);
            }
        }
        return null;
    }

    private void insererEquipeDansLaBaseDeDonnees(String teamName, int championnatid) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetjavafx-3", "root", "")) {
            String sql = "INSERT INTO Teams (teamName, championnatid) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, teamName);
            statement.setInt(2, championnatid);
            statement.executeUpdate();
            System.out.println("Équipe insérée dans la base de données : " + teamName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
