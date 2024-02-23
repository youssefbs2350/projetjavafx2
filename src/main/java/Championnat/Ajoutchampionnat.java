package Championnat;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.*;
public class Ajoutchampionnat extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetjavafx", "root", "");
       // ImageView background = new ImageView(new Image(getClass().getResourceAsStream("C:\\Users\\Administrator\\Desktop\\5571144.jpg")));
        Label titleLabel = new Label("Ajout d'un Championnat");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        Label emptyLabel1 = new Label(); // Ajout d'un label vide
        Label emptyLabel2 = new Label(); // Ajout d'un label vide
        Label emptyLabel3 = new Label(); // Ajout d'un label vide
        Label emptyLabel4 = new Label(); // Ajout d'un label vide
        Label emptyLabel5 = new Label(); // Ajout d'un label vide
        Label emptyLabel6 = new Label(); // Ajout d'un label vide
        Label labelNomChampionnat = new Label("Nom du championnat :");
        labelNomChampionnat.setStyle("-fx-font-weight: bold;"); // Mise en gras du texte
        TextField textFieldNomChampionnat = new TextField();
        textFieldNomChampionnat.setPrefWidth(20);
        Button boutonConfirmer = new Button("Confirmer");
        boutonConfirmer.setOnAction(event -> {
            String nomChampionnat = textFieldNomChampionnat.getText();
             insererChampionnatDansLaBaseDeDonnees(nomChampionnat);
            System.out.println("Nom du championnat confirmé : " + nomChampionnat);
        });
        Button retourchampioonat = new Button("Championnat List");
        retourchampioonat.setOnAction(event -> {
            primaryStage.close();
            Championnat retourpage = new Championnat();
            try {
                retourpage.start(new Stage());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        boutonConfirmer.setStyle("-fx-background-color: #7DBC22; -fx-text-fill: white; -fx-font-weight: bold;");
        retourchampioonat.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");
        VBox root = new VBox();
        HBox titleBox = new HBox(titleLabel); // Créer une HBox pour le titre
        titleBox.setStyle("-fx-alignment: center;"); // Centrer le titre horizontalement
        HBox buttonsBox = new HBox(boutonConfirmer, retourchampioonat);
        VBox.setMargin(buttonsBox, new Insets(0, 0, 10, 30));
        buttonsBox.setAlignment(Pos.CENTER_LEFT);
        buttonsBox.setSpacing(400); // Espacement entre les boutons
        root.getChildren().addAll(titleBox, emptyLabel1, emptyLabel2, emptyLabel3, labelNomChampionnat, emptyLabel6, textFieldNomChampionnat , emptyLabel4, emptyLabel5 , buttonsBox);
        Scene scene = new Scene(root, 720, 600);
        // Configuration de la scène et affichage de la fenêtre
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ajouter un championnat");
        primaryStage.show();
    }
    private void insererChampionnatDansLaBaseDeDonnees(String nomChampionnat) {
        try {
         //   Connexion à la base de données (commenté pour cet exemple)
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetjavafx", "root", "");
            String sql = "INSERT INTO championship (championship_name) VALUES (?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nomChampionnat);
            statement.executeUpdate();
        //    System.out.println("Championnat inséré dans la base de données : " + nomChampionnat);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
