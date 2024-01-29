package com.example.javafxprojet2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {

    @FXML
    private Label welcomeText;

    @FXML
    private void onHelloButtonClick() {
        welcomeText.setText("Hello, JavaFX!");
    }
}
