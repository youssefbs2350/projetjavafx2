/*
package Championnat;
import authentificationetajoututilisateur.Home;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.*;
import java.time.LocalDate;

import javafx.application.Application;
import javafx.stage.Stage;

public class ModifierChampionnat extends Application {


    public ModifierChampionnat(String idChampionnat) {
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetjavafx", "root", "");
                // Obtenir la date système
                Date dateSysteme = Date.valueOf(LocalDate.now());
                String sql = "SELECT * FROM championship where  ";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, nomChampionnat);
                statement.setDate(2, dateSysteme); // Insérer la date système
                statement.setString(3, textFieldType);
                statement.executeUpdate();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox();
        Scene scene = new Scene(root, 720, 500);
        primaryStage.setTitle("Championnat");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

 */
