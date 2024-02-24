package Teams;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChampionnatSelector {

    public static VBox createChampionnatSelector(String url, String utilisateur, String motDePasse) {
        VBox vbox = new VBox(10);

        try {

            Connection connexion = DriverManager.getConnection(url, utilisateur, motDePasse);
            Statement statement = connexion.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT championnatName FROM championnat");

            // Création d'une liste pour stocker les noms des championnats
            List<String> championnats = new ArrayList<>();
            while (resultSet.next()) {
                championnats.add(resultSet.getString("championnatName"));
            }

            // Création du ComboBox avec les championnats
            ComboBox<String> comboBoxChampionnats = new ComboBox<>(FXCollections.observableArrayList(championnats));

            // Label pour afficher le championnat sélectionné
            Label labelNomChampionnat = new Label("Nom du championnat :");
            labelNomChampionnat.setStyle("-fx-font-weight: bold;"); // Mise en gras du texte

            // Écouteur pour le changement de sélection dans le ComboBox
            comboBoxChampionnats.setOnAction(e -> {
                // Mettre à jour le label avec le championnat sélectionné
                labelNomChampionnat.setText("Nom du championnat sélectionné : " + comboBoxChampionnats.getValue());
            });

            vbox.getChildren().addAll(labelNomChampionnat, comboBoxChampionnats);

            connexion.close(); // Fermeture de la connexion à la base de données
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return vbox;

    }
}

