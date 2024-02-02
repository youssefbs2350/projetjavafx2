package authentificationetajoututilisateur;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
        primaryStage.setTitle("Homepage");

        Label messageLabel = new Label(message);

        // Changer la couleur et la taille du texte
        messageLabel.setTextFill(Color.WHITE); // Changer la couleur du texte en blanc
        messageLabel.setFont(new Font(50)); // Changer la taille de la police

        // Boutons
        Button equipeButton = new Button("Équipe");
        Button championnatButton = new Button("Championnat");
        Button matchButton = new Button("Match");
        Button detailsMatchButton = new Button("Détails Match");

        equipeButton.setOnAction(e -> System.out.println("Équipe")); // Ajoutez votre logique ici
        championnatButton.setOnAction(e -> System.out.println("Championnat")); // Ajoutez votre logique ici
        matchButton.setOnAction(e -> System.out.println("Match")); // Ajoutez votre logique ici
        detailsMatchButton.setOnAction(e -> System.out.println("Détails Match")); // Ajoutez votre logique ici

        Button fermerButton = new Button("Fermer");
        fermerButton.setOnAction(e -> primaryStage.close());

        // HBox avec un espacement plus important
        HBox buttonsBox = new HBox(50); // Ajustez cette valeur pour définir l'espacement
        buttonsBox.getChildren().addAll(equipeButton, championnatButton, matchButton, detailsMatchButton);
        buttonsBox.setAlignment(Pos.CENTER); // Aligner au centre

        // Agencement VBox pour le message, les boutons et le bouton Fermer
        VBox layout = new VBox(20); // Ajustez cette valeur pour définir l'espacement
        layout.getChildren().addAll(messageLabel, buttonsBox, fermerButton);
        layout.setPadding(new Insets(20, 20, 20, 20));

        // Chargement de l'image depuis le fichier
        Image img = new Image("img.jpg");
        layout.setBackground(new Background(new BackgroundFill(new ImagePattern(img), null, null)));

        Scene scene = new Scene(layout, 400, 120);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}

