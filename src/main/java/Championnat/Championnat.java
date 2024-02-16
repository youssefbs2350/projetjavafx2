package Championnat;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.*;

public class Championnat extends Application {

    @Override
    public void start(Stage primaryStage) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet championnatid = null;
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetjavafx", "root", "");
        statement = connection.createStatement();
        championnatid = statement.executeQuery("SELECT * FROM championnat");
        Button ajouetrchampionnat = new Button("Ajouter un champpionnat");
        // Créer un TableView
        TableView<String[]> tableView = new TableView<>();

        // Créer les colonnes
        TableColumn<String[], String> column1 = new TableColumn<>("Num de Championnat");
        column1.setCellValueFactory(param -> {
            String[] rowData = param.getValue();
            return rowData != null && rowData.length > 0 ? new javafx.beans.property.SimpleStringProperty(rowData[0]) : null;
        });

        TableColumn<String[], String> column3 = new TableColumn<>("Les Equipes");
        column3.setCellValueFactory(param -> {
            String[] rowData = param.getValue();
            return rowData != null && rowData.length > 0 ? new javafx.beans.property.SimpleStringProperty(rowData[0]) : null;
        });

        TableColumn<String[], String> column2 = new TableColumn<>("Nom du Championnat");
        column2.setCellValueFactory(param -> {
            String[] rowData = param.getValue();
            return rowData != null && rowData.length > 1 ? new javafx.beans.property.SimpleStringProperty(rowData[1]) : null;
        });
        column3.setCellFactory(column -> {
            return new javafx.scene.control.TableCell<>() {
                final Button button = new Button("ICI");

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(button);
                    }
                }
            };
        });
        tableView.getColumns().addAll(column1, column2, column3);
        while (championnatid.next()) {
            tableView.getItems().add(new String[]{championnatid.getString("championnatid"),championnatid.getString("championnatName")});
        }
            ajouetrchampionnat.setOnAction(event -> {
            // Ajoutez ici le code à exécuter lorsque le bouton est cliqué
            System.out.println("Bouton cliqué !");
        });
        VBox root = new VBox(ajouetrchampionnat,tableView);
        Scene scene = new Scene(root, 360, 400);
        primaryStage.setTitle("Championnat");
        primaryStage.setScene(scene);
        primaryStage.show();

        ajouetrchampionnat.setOnAction(event -> {
            Ajoutchampionnat ajoutchampionnat = new Ajoutchampionnat();
            try {
                ajoutchampionnat.start(new Stage());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

}
    public static void main(String[] args) {
        launch(args);
    }
}
