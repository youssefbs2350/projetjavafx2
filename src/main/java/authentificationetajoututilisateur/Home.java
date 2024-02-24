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
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Objects;

public class Home extends Application {
    private String message;
    private String username;

    public Home(String message, String username) {
        this.message = message;
        this.username = username;
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

     /*   championnatButton.setOnAction(e ->{
            Championnat champ = new Championnat();
            Stage stagechamp = new Stage();
            try {
                champ.start(stagechamp);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });*/


        // Nouveau bouton pour modifier les données de l'utilisateur
        Button modifierButton = createButton("Modifier");
        modifierButton.setOnAction(e -> {
            ModifierUtilisateur modifierWindow = null;
            try {
                modifierWindow = new ModifierUtilisateur(username);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            Stage stage2 = new Stage();
            modifierWindow.start(stage2);
        });

        // Actions des boutons
        equipeButton.setOnAction(e -> System.out.println("Équipe"));
        championnatButton.setOnAction(e -> System.out.println("Championnat"));
        matchButton.setOnAction(e -> System.out.println("Match"));
        detailsMatchButton.setOnAction(e -> System.out.println("Détails Match"));

        // Bouton Fermer
        Button fermerButton = new Button("Fermer");
        fermerButton.setOnAction(e -> primaryStage.close());

        fermerButton.setPrefSize(120, 60);

        // HBox pour les boutons horizontaux (sauf le bouton Fermer)
        HBox buttonsBox = new HBox(30);
        buttonsBox.getChildren().addAll(equipeButton, championnatButton, matchButton, detailsMatchButton);
        buttonsBox.setAlignment(Pos.CENTER);

        // HBox pour le nouveau bouton et le bouton Fermer
        HBox bottomBox = new HBox(30);
        bottomBox.getChildren().addAll(modifierButton, new Region(), fermerButton);
        bottomBox.setAlignment(Pos.CENTER); // Centrage des boutons
        HBox.setHgrow(new Region(), Priority.ALWAYS); // Permet au bouton Fermer de rester à droite

        // VBox pour l'ensemble de la mise en page
        VBox layout = new VBox(30);
        layout.getChildren().addAll(messageLabel, buttonsBox);
        layout.getChildren().add(bottomBox);
        layout.setAlignment(Pos.TOP_LEFT); // Aligner au coin supérieur gauche
        layout.setPadding(new Insets(40));

        // Charger l'image depuis le fichier
        Image backgroundImage = new Image("img.jpg");
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        layout.setBackground(new Background(background));

        Scene scene = new Scene(layout, 800, 600);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());
        primaryStage.setScene(scene);

        primaryStage.setMaximized(true);

        primaryStage.show();
    }

    // Méthode utilitaire pour créer des boutons avec un style uniforme
    private Button createButton(String text) {
        Button button = new Button(text);
        button.setPrefSize(120, 60);

        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
