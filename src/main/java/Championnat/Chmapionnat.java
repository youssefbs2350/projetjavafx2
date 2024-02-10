package Championnat;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Chmapionnat extends Application {

    @Override
    public void start(Stage primaryStage) {

        Button ici = new Button("Click ICI");

        // Créer un TableView
        TableView<String[]> tableView = new TableView<>();

        // Créer les colonnes
        TableColumn<String[], String> column1 = new TableColumn<>("Nom de Championnat");
        column1.setCellValueFactory(param -> {
            String[] rowData = param.getValue();
            return rowData != null && rowData.length > 0 ? new javafx.beans.property.SimpleStringProperty(rowData[0]) : null;
        });

        TableColumn<String[], String> column2 = new TableColumn<>("Equipes");
        column2.setCellValueFactory(param -> {
            String[] rowData = param.getValue();
            return rowData != null && rowData.length > 1 ? new javafx.beans.property.SimpleStringProperty(rowData[1]) : null;
        });

        // Ajouter les colonnes à la TableView
        tableView.getColumns().addAll(column1, column2);

        // Ajouter des lignes de données
        for (int i = 0; i < 5; i++) {
            tableView.getItems().add(new String[]{"Football Tournois " , "ICI"});
        }

        // Créer une disposition VBox pour placer la TableView
        VBox root = new VBox(tableView);

        // Créer la scène
        Scene scene = new Scene(root, 700, 400);

        // Configurer le stage
        primaryStage.setTitle("Championnat");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
