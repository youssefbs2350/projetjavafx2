package authentificationetajoututilisateur;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Home extends Application {
    private String message;

    public Home(String message) {
        this.message = message;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Page d'accueil");

        Label messageLabel = new Label(message);
        messageLabel.setTextFill(Color.WHITE);
        messageLabel.setFont(Font.font("Arial", 50));

        // Boutons
        Button equipeButton = createButton("Équipe");
        Button championnatButton = createButton("Championnat");
        Button matchButton = createButton("Match");
        Button detailsMatchButton = createButton("Détails Match");

        // Actions des boutons
        equipeButton.setOnAction(e -> System.out.println("Équipe"));
        championnatButton.setOnAction(e -> System.out.println("Championnat"));
        matchButton.setOnAction(e -> System.out.println("Match"));
        detailsMatchButton.setOnAction(e -> System.out.println("Détails Match"));

        Button fermerButton = new Button("Fermer");
        fermerButton.setOnAction(e -> primaryStage.close());

        fermerButton.setStyle(getClass().getResource("/styles.css").toExternalForm());
        // HBox pour les boutons
        HBox buttonsBox = new HBox(30);
        buttonsBox.getChildren().addAll(equipeButton, championnatButton, matchButton, detailsMatchButton);
        buttonsBox.setAlignment(Pos.CENTER);

        // VBox pour l'ensemble de la mise en page
        VBox layout = new VBox(30);
        layout.getChildren().addAll(messageLabel, buttonsBox, fermerButton);
        layout.setAlignment(Pos.TOP_LEFT); // Aligner au coin supérieur gauche
        layout.setPadding(new Insets(40));

        // Charger l'image depuis le fichier
        Image backgroundImage = new Image("file:C://img.jpg");
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        layout.setBackground(new Background(background));

        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setScene(scene);

        primaryStage.setMaximized(true);

        primaryStage.show();
    }

    // Méthode utilitaire pour créer des boutons avec un style uniforme
    private Button createButton(String text) {
        Button button = new Button(text);
        button.setPrefSize(180, 60);
        button.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
