package authentificationetajoututilisateur;

import DetailsMatch.DetailsMatchApp;
import javafx.application.Application;
import javafx.stage.Stage;


public class MainApp extends Application {

    /*public static void main(String[] args) {
        launch(args);
    }*/

    @Override
    public void start(Stage primaryStage) {

        AuthentificationAp authApp = new AuthentificationAp();
        //DetailsMatchApp detailsMatchApp = new DetailsMatchApp();
        authApp.start(primaryStage);
        //detailsMatchApp.start(primaryStage);
    }
}