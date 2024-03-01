package authentificationetajoututilisateur;

import Match.MatchWindow;
import Teams.Listteamuser;
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class HomeUser extends Application {
    private String message;
    private String username;

    public HomeUser(String message, String username) {
        this.message = message;
        this.username = username;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Page d'accueil user");

        Label messageLabel = new Label(message);
        messageLabel.setTextFill(Color.WHITE);
        messageLabel.setFont(Font.font("Arial", 50));

        // Boutons
        Button equipeButton = createButton("Équipe");
        equipeButton.setOnAction(event -> {
            try {
                String fxmlDocPath = "D:\\Users\\youss\\.jdks\\jbr-17.0.8\\bin\\gitfx\\projetjavafx3\\src\\main\\java\\Teams\\listteamuser.fxml";
                javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader();
                FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
                AnchorPane root = loader.load(fxmlStream);

                Scene scene = new Scene(root, 709, 494);
                primaryStage.setScene(scene);
                primaryStage.setResizable(true);
                primaryStage.setTitle("équipe");
                primaryStage.show();


                // Obtenez une référence à la classe Listteam depuis le chargeur FXML
                Listteamuser listTeamController = loader.getController();

                // Passez le nom d'utilisateur à la classe Listteam
                listTeamController.getUsername();


            } catch (IOException e) {
                e.printStackTrace();
                // Gérer l'exception, par exemple afficher un message d'erreur à l'utilisateur
            }
        });
        equipeButton.getStyleClass().add("detailsMatchButton");
<<<<<<< HEAD
      //  Button championnatButton = createButton("Championnat");
      //  championnatButton.getStyleClass().add("detailsMatchButton");
=======
        //Button championnatButton = createButton("Championnat");
     //   championnatButton.getStyleClass().add("detailsMatchButton");
>>>>>>> bd3c5ecc1f7a58862208318e144ae2331d45bbb1
        Button matchButton = createButton("Match");
        matchButton.getStyleClass().add("detailsMatchButton");
        Button detailsMatchButton = createButton("Détails Match");
        detailsMatchButton.getStyleClass().add("detailsMatchButton");

        // Bouton Fermer
        Button fermerButton = new Button("Fermer");
        fermerButton.getStyleClass().add("fermerButton");
        fermerButton.setOnAction(e -> primaryStage.close());
        fermerButton.setPrefSize(120, 60);

        // Nouveau bouton pour modifier les données de l'utilisateur
        Button modifierButton = new Button("Modifier");
        modifierButton.getStyleClass().add("matchButton");
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
        modifierButton.setPrefSize(120, 60);

        matchButton.setOnAction(e -> {
            MatchWindow matchWindow = null;
            try {
                matchWindow = new MatchWindow();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            Stage stage3 = new Stage();
            matchWindow.start(stage3);
        });
        matchButton.setPrefSize(120, 60);

        // HBox pour les boutons horizontaux (sauf le bouton Fermer)
        HBox buttonsBox = new HBox(75);
<<<<<<< HEAD
        buttonsBox.getChildren().addAll(equipeButton,matchButton, detailsMatchButton);
=======
        buttonsBox.getChildren().addAll(equipeButton, /*championnatButton,*/ matchButton, detailsMatchButton);
>>>>>>> bd3c5ecc1f7a58862208318e144ae2331d45bbb1
        buttonsBox.setAlignment(Pos.CENTER);

        // HBox pour le nouveau bouton et le bouton Fermer
        HBox bottomBox = new HBox(575);
        bottomBox.getChildren().addAll(modifierButton, new Region(), fermerButton);
        bottomBox.setAlignment(Pos.CENTER); // Centrage des boutons
        HBox.setHgrow(new Region(), Priority.ALWAYS); // Permet au bouton Fermer de rester à droite

        // Création du VBox pour le bottomBox seul
        VBox bottomVBox = new VBox();
        bottomVBox.getChildren().addAll(bottomBox);
        bottomVBox.setMargin(bottomBox, new Insets(0, 0, 600, 0));

        // VBox pour l'ensemble de la mise en page
        VBox layout = new VBox(180);
        layout.getChildren().addAll(messageLabel, buttonsBox, new Region(), bottomVBox);
        layout.setAlignment(Pos.TOP_LEFT);

        // Charger l'image depuis le fichier
        Image backgroundImage = new Image("img.jpg");
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        layout.setBackground(new Background(background));
        //  VBox.setMargin(bottomBox, new Insets(0, 0, 100, 0));

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
