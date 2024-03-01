package Teams;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text; // Import the Text class
import javafx.stage.Stage;

public class MessageWindow {

    @FXML
    private Button closeButton;

    @FXML
    private Text messageText; // Specify the correct type

    public void setMessage(String message) {
        messageText.setText(message);
    }

    @FXML
    private void initialize() {
        closeButton.setOnAction(event -> closeStage());
    }

    private void closeStage() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
