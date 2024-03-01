package authentificationetajoututilisateur;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.util.Date;
//import java.sql.Date;

public class Match extends Application {
    private int match_id;
    private int team_A_id;
    private int team_B_id;
    private int team_A_score;
    private int team_B_score;
    private String field;
    private Date date_match;
    private String city;
    private int referee_id;

    FXMLLoader loader = new FXMLLoader();

    public Match() {
    }

    @Override
    public void start(Stage stage) throws Exception {

        String fxmlDocPath ="C:\\Users\\ThinkPad\\Documents\\esprit\\projetjavafx2-main\\src\\main\\java\\authentificationetajoututilisateur\\Match.fxml";
        FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);

        AnchorPane root = (AnchorPane) loader.load(fxmlStream);
        Scene scene = new Scene(root, 1280, 622);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Authentification JavaFX");
        stage.show();
    }

    public Match(int team_A_id, int team_B_id, String field, Date date_match, String city, int referee_id) {
        this.team_A_id = team_A_id;
        this.team_B_id = team_B_id;
        this.field = field;
        this.date_match = date_match;
        this.city = city;
        this.referee_id = referee_id;
    }

    // Getters and Setters for all fields
    public int getMatch_id() {
        return match_id;
    }

    public void setMatch_id(int match_id) {
        this.match_id = match_id;
    }

    public int getTeam_A_id() {
        return team_A_id;
    }

    public void setTeam_A_id(int team_A_id) {
        this.team_A_id = team_A_id;
    }

    public int getTeam_B_id() {
        return team_B_id;
    }

    public void setTeam_B_id(int team_B_id) {
        this.team_B_id = team_B_id;
    }

    public int getTeam_A_score() {
        return team_A_score;
    }

    public void setTeam_A_score(int team_A_score) {
        this.team_A_score = team_A_score;
    }

    public int getTeam_B_score() {
        return team_B_score;
    }

    public void setTeam_B_score(int team_B_score) {
        this.team_B_score = team_B_score;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Date getDate_match() {
        return date_match;
    }

    public void setDate_match(Date date_match) {
        this.date_match = date_match;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getReferee_id() {
        return referee_id;
    }

    public void setReferee_id(int referee_id) {
        this.referee_id = referee_id;
    }
}
