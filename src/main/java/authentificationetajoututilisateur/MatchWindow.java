
/* import authentificationetajoututilisateur.Authentification;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.MatchResult;

public class MatchWindow extends Application {
    private Connection connection;

    public MatchWindow() throws SQLException {
        connection = Authentification.getInstance().getConnection();
    }
    FXMLLoader loader = new FXMLLoader();
    @FXML
    private TableView<Match> MatchsTableView;
    @FXML
    private TableColumn<Match, Integer> matchIdColumn;
    @FXML
    private TableColumn<Match, String> teamANameColumn;
    @FXML
    private TableColumn<Match, String> teamBNameColumn;
    @FXML
    private TableColumn<Match, Integer> teamAScoreColumn;
    @FXML
    private TableColumn<Match, Integer> teamBScoreColumn;
    @FXML
    private TableColumn<Match, String> fieldColumn;
    @FXML
    private TableColumn<Match, String> dateMatchColumn;
    @FXML
    private TableColumn<Match, String> cityColumn;
    @FXML
    private TableColumn<Match, Integer> refereeIdColumn;

    @FXML
    private TableView<Match> upcomingMatchesTableView;
    @FXML
    private TableColumn<Match, String> upcomingTeamANameColumn;
    @FXML
    private TableColumn<Match, String> upcomingTeamBNameColumn;
    @FXML
    private TableColumn<Match, String> upcomingFieldColumn;
    @FXML
    private TableColumn<Match, String> upcomingDateMatchColumn;
    @FXML
    private TableColumn<Match, String> upcomingCityColumn;
    @Override
    public void start(Stage primaryStage) {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("match_window.fxml");
            Parent root = loader.load(inputStream);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        // Configurez les cellules de la TableView
        matchIdColumn.setCellValueFactory(cellData -> cellData.getValue().matchIdProperty().asObject());
        teamANameColumn.setCellValueFactory(cellData -> cellData.getValue().teamANameProperty());
        teamBNameColumn.setCellValueFactory(cellData -> cellData.getValue().teamBNameProperty());
        teamAScoreColumn.setCellValueFactory(cellData -> cellData.getValue().teamAScoreProperty().asObject());
        teamBScoreColumn.setCellValueFactory(cellData -> cellData.getValue().teamBScoreProperty().asObject());
        fieldColumn.setCellValueFactory(cellData -> cellData.getValue().fieldProperty());
        dateMatchColumn.setCellValueFactory(cellData -> cellData.getValue().dateMatchProperty());
        cityColumn.setCellValueFactory(cellData -> cellData.getValue().cityProperty());
        refereeIdColumn.setCellValueFactory(cellData -> cellData.getValue().refereeIdProperty().asObject());
        upcomingTeamANameColumn.setCellValueFactory(cellData -> cellData.getValue().teamANameProperty());
        upcomingTeamBNameColumn.setCellValueFactory(cellData -> cellData.getValue().teamBNameProperty());
        upcomingFieldColumn.setCellValueFactory(cellData -> cellData.getValue().fieldProperty());
        upcomingDateMatchColumn.setCellValueFactory(cellData -> cellData.getValue().dateMatchProperty());
        upcomingCityColumn.setCellValueFactory(cellData -> cellData.getValue().cityProperty());

        try {
            System.out.println("test AAA");

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT m.match_id, t1.team_name AS teamAName, t2.team_name AS teamBName, m.team_A_score, m.team_B_score, m.field, m.date_match, m.city, m.referee_id FROM `match` m JOIN `team` t1 ON m.team_A_id = t1.team_id JOIN `team` t2 ON m.team_B_id = t2.team_id WHERE date_match <= CURRENT_DATE()");
            //ResultSet resultSet = statement.executeQuery("SELECT * FROM `match` WHERE team_A_score IS NOT NULL AND team_B_score IS NOT NULL");
            System.out.println("test BBB");
            System.out.println(resultSet);

            while (resultSet.next()) {
                Match match = new Match(
                        resultSet.getInt("match_id"),
                        resultSet.getString("teamAName"),
                        resultSet.getString("teamBName"),
                        resultSet.getInt("team_A_score"),
                        resultSet.getInt("team_B_score"),
                        resultSet.getString("field"),
                        resultSet.getString("date_match"),
                        resultSet.getString("city"),
                        resultSet.getInt("referee_id")
                );
                matchResultsTableView.getItems().add(matchResult);
            }
            // Sélectionnez les matchs à venir
            resultSet = statement.executeQuery("SELECT m.match_id, t1.team_name AS teamAName, t2.team_name AS teamBName, m.team_A_score, m.team_B_score, m.field, m.date_match, m.city, m.referee_id FROM `match` m JOIN `team` t1 ON m.team_A_id = t1.team_id JOIN `team` t2 ON m.team_B_id = t2.team_id WHERE date_match > CURRENT_DATE()");
            //resultSet = statement.executeQuery("SELECT * FROM `match` WHERE team_A_score IS NULL AND team_B_score IS NULL");
            while (resultSet.next()) {
                MatchResult matchResult = new MatchResult(
                        resultSet.getInt("match_id"),
                        resultSet.getString("teamAName"),
                        resultSet.getString("teamBName"),
                        resultSet.getInt("team_A_score"),
                        resultSet.getInt("team_B_score"),
                        resultSet.getString("field"),
                        resultSet.getString("date_match"),
                        resultSet.getString("city"),
                        resultSet.getInt("referee_id")
                );
                upcomingMatchesTableView.getItems().add(matchResult);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
*/