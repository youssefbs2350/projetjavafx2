

package Teams;
import javafx.application.Application;
import javafx.stage.Stage;


public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Ajout_Team Ajout_Team = new Ajout_Team();
        try {
            Ajout_Team.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
