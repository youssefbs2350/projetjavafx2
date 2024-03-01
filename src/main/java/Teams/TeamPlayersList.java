package Teams;

import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableRow;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeamPlayersList {
    private String teamName;

    public TeamPlayersList(String teamName) {
        this.teamName = teamName;
    }

    public void showPlayers() {
        TableView<String> tableView1 = new TableView<>();
        TableColumn<String, String> playerNameColumn = new TableColumn<>("Player Name");
        playerNameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()));
        tableView1.getColumns().add(playerNameColumn);

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetjavafx-3", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT username FROM utilisateurs WHERE teamid IN (SELECT teamid FROM teams WHERE teamName = ?)")) {
            preparedStatement.setString(1, teamName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    tableView1.getItems().add(resultSet.getString("username"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tableView1.setRowFactory(tableView -> new TableRow<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (getIndex() % 2 == 0) {
                    // Apply green background to even rows
                    setStyle("-fx-background-color: #D4EED1;");
                } else {
                    // Apply light green background to odd rows
                    setStyle("-fx-background-color: #C3F6BF;");
                }
            }
        });

        VBox root = new VBox(tableView1);
        Scene scene = new Scene(root, 300, 400);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Players List : " + teamName);
        stage.show();
    }
}
