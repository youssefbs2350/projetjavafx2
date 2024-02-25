package authentificationetajoututilisateur;/*package authentificationetajoututilisateur;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        AuthentificationAp authApp = new AuthentificationAp();
        authApp.start(primaryStage);
    }
}*/

/*import java.io.FileInputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
public class MainApp extends Application {
   FXMLLoader loader = new FXMLLoader();
    public static Stage stage;
   @Override
   public void start(Stage stage) {
       try {
           String fxmlDocPath = "D:\\Users\\youss\\.jdks\\jbr-17.0.8\\bin\\gitfx\\projetjavafx3\\src\\main\\java\\authentificationetajoututilisateur\\AuthentificationAp.fxml";
           FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
           AnchorPane root = (AnchorPane) loader.load(fxmlStream);
           Scene scene = new Scene(root,612,293);
           stage.setScene(scene);
           stage.setResizable(false);
           stage.setTitle("Authentification JavaFX");
           stage.show();


       } catch(Exception e) {
           e.printStackTrace();
       }
    }}*/

import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
        import javafx.scene.layout.AnchorPane;
        import javafx.scene.layout.StackPane;
        import javafx.scene.media.Media;
        import javafx.scene.media.MediaPlayer;
        import javafx.scene.media.MediaView;
        import javafx.stage.Stage;
        import javafx.util.Duration;
        import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MainApp extends Application {
    FXMLLoader loader = new FXMLLoader();
    public static Stage stage;

    @Override
    public void start(Stage primaryStage) {
        try {
            stage = primaryStage;

            // Chemin de la vidéo d'introduction
            String introVideoPath = "D:\\Users\\youss\\.jdks\\jbr-17.0.8\\bin\\gitfx\\projetjavafx3\\src\\main\\java\\authentificationetajoututilisateur\\intro.mp4";
            Media introMedia = new Media(new File(introVideoPath).toURI().toString());
            MediaPlayer introPlayer = new MediaPlayer(introMedia);
            MediaView introView = new MediaView(introPlayer);

            // Charger le fichier FXML après la vidéo d'introduction
            introPlayer.setOnEndOfMedia(() -> {
                try {
                    String fxmlDocPath = "D:\\Users\\youss\\.jdks\\jbr-17.0.8\\bin\\gitfx\\projetjavafx3\\src\\main\\java\\authentificationetajoututilisateur\\AuthentificationAp.fxml";
                    FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
                    AnchorPane root = (AnchorPane) loader.load(fxmlStream);
                    Scene scene = new Scene(root, 1280, 620);

                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.setTitle("Authentification JavaFX");
                    stage.show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            // Afficher la vidéo d'introduction
            StackPane introRoot = new StackPane();
            introRoot.getChildren().add(introView);
            Scene introScene = new Scene(introRoot, 1280, 720);
            primaryStage.setScene(introScene);
            stage.setResizable(false);
            stage.setTitle("welcome to our Application");
            primaryStage.show();

            // Lecture de la vidéo d'introduction
            introPlayer.play();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
