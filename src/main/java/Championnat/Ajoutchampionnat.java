package Championnat;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import java.io.File;
import java.sql.*;
public class Ajoutchampionnat extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetjavafx", "root", "");
        Label titleLabel = new Label("Ajout d'un Championnat");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        Label emptyLabel1 = new Label();
        Label emptyLabel2 = new Label();
        Label emptyLabel3 = new Label();
        Label emptyLabel4 = new Label();
        Label emptyLabel5 = new Label();
        Label emptyLabel6 = new Label();
        Label emptyLabel7 = new Label();
        Label labelNomChampionnat = new Label("Nom du championnat :");
        labelNomChampionnat.setStyle("-fx-font-weight: bold;"); // Mise en gras du texte

        TextField textFieldNomChampionnat = new TextField();
        Label labelDate = new Label("Date du championnat :");
        labelDate.setStyle("-fx-font-weight: bold;");
        DatePicker datePicker = new DatePicker();
        Label labelType = new Label("Type du championnat :");
        labelType.setStyle("-fx-font-weight: bold;");
        TextField textFieldType = new TextField();
        textFieldNomChampionnat.setPrefWidth(20);
        Button boutonConfirmer = new Button("Confirmer");
        boutonConfirmer.setOnAction(event -> {
            String nomChampionnat = textFieldNomChampionnat.getText();
            Date dateChampionnat = datePicker.getValue() != null ? Date.valueOf(datePicker.getValue()) : null;
            String typeChampionnat = textFieldType.getText();
            insererChampionnatDansLaBaseDeDonnees(nomChampionnat, dateChampionnat, typeChampionnat);
            // Votre code existant...
        });
        boutonConfirmer.setStyle("-fx-background-color: #7DBC22; -fx-text-fill: white; -fx-font-weight: bold;");
        VBox root = new VBox();
        HBox titleBox = new HBox(titleLabel); // Créer une HBox pour le titre
        root.setBackground(new Background(new BackgroundImage(
                new Image(new File("C:\\Users\\Administrator\\Desktop\\1.jpg").toURI().toString(), true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
        titleBox.setStyle("-fx-alignment: center;"); // Centrer le titre horizontalement
        HBox buttonsBox = new HBox(boutonConfirmer);
        VBox.setMargin(buttonsBox, new Insets(0, 0, 10, 30));
        buttonsBox.setAlignment(Pos.CENTER_LEFT);
        buttonsBox.setSpacing(400); // Espacement entre les boutons
        root.getChildren().addAll(titleBox, emptyLabel1, emptyLabel2, emptyLabel3, labelNomChampionnat, emptyLabel4, textFieldNomChampionnat , emptyLabel5,labelType ,emptyLabel6, datePicker, emptyLabel7 , buttonsBox);
        Scene scene = new Scene(root, 720, 600);
        // Configuration de la scène et affichage de la fenêtre
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ajouter un championnat");
        primaryStage.show();
    }
    private void insererChampionnatDansLaBaseDeDonnees(String nomChampionnat, Date dateAttribut, String typeAttribut) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetjavafx", "root", "");
            String sql = "INSERT INTO championship (championship_name, date_attribut, type_attribut) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nomChampionnat);
            statement.setDate(2, dateAttribut);
            statement.setString(3, typeAttribut);
            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
