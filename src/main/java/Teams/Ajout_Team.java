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
import javafx.scene.control.ComboBox;

import java.sql.*;
import java.util.Map;

public class Ajout_Team extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Connexion à la base de données
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetjavafx-3", "root", "");

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
            Button retourEquipe = new Button("Liste des équipes");

            retourEquipe.setOnAction(event -> {
                primaryStage.close();
                TeamsList ajoutTeam = new TeamsList();
                ajoutTeam.start(new Stage());
            });

            boutonConfirmer.setStyle("-fx-background-color: #7DBC22; -fx-text-fill: white; -fx-font-weight: bold;");
            retourEquipe.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");

            VBox root = new VBox();
            HBox titleBox = new HBox(titleLabel); // Créer une HBox pour le titre
            titleBox.setStyle("-fx-alignment: center;"); // Centrer le titre horizontalement

            HBox buttonsBox = new HBox(boutonConfirmer, retourEquipe);
            VBox.setMargin(buttonsBox, new Insets(0, 0, 10, 30));
            buttonsBox.setAlignment(Pos.CENTER_LEFT);
            buttonsBox.setSpacing(400); // Espacement entre les boutons

            // Écouteur pour le bouton "Confirmer"
            boutonConfirmer.setOnAction(event -> {
                String teamName = textFieldNomEquipe.getText();
                int championnatId = getChampionnatId(championnatSelector);
                insererEquipeDansLaBaseDeDonnees(teamName, championnatId);
                System.out.println("Équipe créée : " + teamName);
            });

            root.getChildren().addAll(titleBox, labelNomEquipe, textFieldNomEquipe, labelChampionnat, championnatSelector, buttonsBox);
            Scene scene = new Scene(root, 720, 600);

            // Configuration de la scène et affichage de la fenêtre
            primaryStage.setScene(scene);
            primaryStage.setTitle("Ajouter une équipe");
            primaryStage.show();

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
