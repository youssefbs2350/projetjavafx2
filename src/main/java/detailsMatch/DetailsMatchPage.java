package detailsMatch;

import authentificationetajoututilisateur.Home;
import javafx.application.Application;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.time.LocalDate;

public class DetailsMatchPage extends Application {

    private TextField matchIdField;
    private TextField terrainField;
    private DatePicker datePicker;
    private TextField timeField;
    private TextField cityField;
    private TextField refereeIdField;
    private Label statusLabel;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Details Match");

        // Create UI elements for the Details Match page
        Label titleLabel = new Label("Details Match");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        matchIdField = new TextField();
        terrainField = new TextField();
        datePicker = new DatePicker();
        timeField = new TextField();
        cityField = new TextField();
        refereeIdField = new TextField();

        Label matchIdLabel = new Label("Match ID:");
        Label terrainLabel = new Label("Terrain:");
        Label dateLabel = new Label("Date:");
        Label timeLabel = new Label("Time:");
        Label cityLabel = new Label("City:");
        Label refereeIdLabel = new Label("Referee ID:");

        Button saveButton = new Button("Save");
        Button backButton = new Button("Back to Home");

        statusLabel = new Label();
        statusLabel.setTextFill(Color.RED);

        // Set actions for buttons
        saveButton.setOnAction(e -> saveDetailsMatch());
        backButton.setOnAction(e -> {
            Home home = new Home("Welcome to Home");
            Stage stage = new Stage();
            home.start(stage);
            primaryStage.close();
        });

        // Layout
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));
        gridPane.addRow(0, titleLabel);
        gridPane.addRow(1, matchIdLabel, matchIdField);
        gridPane.addRow(2, terrainLabel, terrainField);
        gridPane.addRow(3, dateLabel, datePicker);
        gridPane.addRow(4, timeLabel, timeField);
        gridPane.addRow(5, cityLabel, cityField);
        gridPane.addRow(6, refereeIdLabel, refereeIdField);
        gridPane.addRow(7, saveButton, backButton);
        gridPane.addRow(8, statusLabel);

        // Set scene
        Scene scene = new Scene(gridPane, 400, 350);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void saveDetailsMatch() {
        if (validateInput()) {
            // Save details match data
            displayStatusMessage("Details Match data saved successfully!", Color.GREEN);
            clearFields();
        } else {
            displayStatusMessage("Please fill in all fields correctly.", Color.RED);
        }
    }

    private boolean validateInput() {
        // Basic validation for non-empty fields
        return !matchIdField.getText().isEmpty()
                && !terrainField.getText().isEmpty()
                && datePicker.getValue() != null
                && !timeField.getText().isEmpty()
                && !cityField.getText().isEmpty()
                && !refereeIdField.getText().isEmpty();
    }

    private void clearFields() {
        matchIdField.clear();
        terrainField.clear();
        datePicker.setValue(null);
        timeField.clear();
        cityField.clear();
        refereeIdField.clear();
    }

    private void displayStatusMessage(String message, Color color) {
        statusLabel.setText(message);
        statusLabel.setTextFill(color);
    }


}

