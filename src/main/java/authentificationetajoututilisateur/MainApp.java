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

/*package authentificationetajoututilisateur;
import javafx.application.Application;

import javafx.stage.Stage;


public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
    /*    FXMLLoader loader =  FXMLLoader.load(getClass().getResource("AuthentificationAp.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }*/

import java.io.FileInputStream;
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
    }}
