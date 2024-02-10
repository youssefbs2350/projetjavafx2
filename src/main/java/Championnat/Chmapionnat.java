package Championnat;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Chmapionnat extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Créer un TableView
        TableView<String[]> tableView = new TableView<>();

        // Créer les colonnes
        TableColumn<String[], String> column1 = new TableColumn<>("Colonne 1");
        column1.setCellValueFactory(param -> {
            String[] rowData = param.getValue();
            return rowData != null && rowData.length > 0 ? new javafx.beans.property.SimpleStringProperty(rowData[0]) : null;
        });

        TableColumn<String[], String> column2 = new TableColumn<>("Colonne 2");
        column2.setCellValueFactory(param -> {
            String[] rowData = param.getValue();
            return rowData != null && rowData.length > 1 ? new javafx.beans.property.SimpleStringProperty(rowData[1]) : null;
        });

        // Ajouter les colonnes à la TableView
        tableView.getColumns().addAll(column1, column2);

        // Ajouter des lignes de données
        for (int i = 0; i < 10; i++) {
            tableView.getItems().add(new String[]{"Ligne " + (i + 1) + " Colonne 1", "Ligne " + (i + 1) + " Colonne 2"});
        }

        // Créer une disposition VBox pour placer la TableView
        VBox root = new VBox(tableView);

        // Créer la scène
        Scene scene = new Scene(root, 400, 300);

        // Configurer le stage
        primaryStage.setTitle("Tableau JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
