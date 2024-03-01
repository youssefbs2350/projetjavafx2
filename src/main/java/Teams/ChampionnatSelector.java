package Teams;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ChampionnatSelector {

    public static VBox createChampionnatSelector(String url, String utilisateur, String motDePasse) {
        VBox vbox = new VBox(10);

        try {
            Connection connexion = DriverManager.getConnection(url, utilisateur, motDePasse);
            Statement statement = connexion.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT championnatid, championnatName FROM championnat");

            Map<Integer, String> championnatsMap = new HashMap<>();
            while (resultSet.next()) {
                int championnatid = resultSet.getInt("championnatid");
                String championnatName = resultSet.getString("championnatName");
                championnatsMap.put(championnatid, championnatName);
            }

            ComboBox<Map.Entry<Integer, String>> comboBoxChampionnats = new ComboBox<>(FXCollections.observableArrayList(championnatsMap.entrySet()));
            comboBoxChampionnats.setConverter(new ComboBoxStringConverter());

            vbox.getChildren().addAll(comboBoxChampionnats);

            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vbox;
    }
}
