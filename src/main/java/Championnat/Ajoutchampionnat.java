package Championnat;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
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
import java.time.LocalDate;
public class Ajoutchampionnat extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws SQLException {
     //   Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetjavafx", "root", "");
        Label titleLabel = new Label("Ajout d'un Championnat");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        Label emptyLabel1 = new Label();
        Label emptyLabel2 = new Label();
        Label emptyLabel3 = new Label();
        Label emptyLabel4 = new Label();
        Label emptyLabel5 = new Label();
        Label emptyLabel6 = new Label();
        Label emptyLabel7= new Label();
        Label emptyLabel8 = new Label();
        Label emptyLabel9 = new Label();
        Label emptyLabel10 = new Label();
        Label emptyLabel11 = new Label();
        Label emptyLabel12 = new Label();
        Label labelNomChampionnat = new Label("  Nom du championnat :");
        Label labelType = new Label("  Type du championnat :");
        labelNomChampionnat.setStyle("-fx-font-weight: bold;"); // Mise en gras du texte
        TextField nom = new TextField();
        ComboBox<String> typeComboBox = new ComboBox<>();
        typeComboBox.setValue("Sélectionnez un type de sport :");
        typeComboBox.getItems().addAll(
                "Handball",
                "Hockey sur gazon",
                "Hockey sur glace",
                "Rugby à VII",
                "Volley-ball",
                "Water-polo",
                "Baseball",
                "Softball ",
                "Football",
                "Natation synchronisée",
                "Basketball",
                "Natation",
                "Sports non olympiques",
                "Balle à la main",
                "Balle aux prisonniers",
                "Balle au tamis",
                "Balle pelote",
                "Ballon au poing",
                "Bandy",
                "Baseball",
                "Beach handball",
                "Beach soccer",
                "Cricket",
                "Fistball",
                "Floorball",
                "Football américain",
                "Football australien",
                "Football en salle",
                "Football gaélique",
                "Hockey cosom",
                "Hockey subaquatique",
                "Horse-ball",
                "Kayak-polo",
                "Kin-ball",
                "Korfball",
                "Netball",
                "Polo",
                "Poull-Ball",
                "Rafroball",
                "Ringuette",
                "Rink hockey",
                "Roller in line hockey (RILH)",
                "Roller Derby",
                "Patinage synchronisé (Roller artistique en groupe)",
                "Rugby à XIII",
                "Rugby à XV",
                "Sepak takraw",
                "Smolball",
                "Snow Volleyball",
                "Speed Corner",
                "Softball",
                "Tchoukball",
                "Ultimate Frisbee",
                "Water Volleyball"
        );
        DatePicker datePicker = new DatePicker();
        labelType.setStyle("-fx-font-weight: bold;");
        Button boutonConfirmer = new Button("Confirmer");
        boutonConfirmer.setOnAction(event -> {
            String nomChampionnat = nom.getText();
            String typeChampionnat = typeComboBox.getValue();
            if (nomChampionnat.isEmpty()||(typeChampionnat=="Sélectionnez un type de sport :")) {
                System.out.println("maher");
                // Afficher un message d'erreur à l'utilisateur
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs obligatoires.");
                alert.showAndWait();
            } else {
                insert(nomChampionnat, typeChampionnat);
                primaryStage.close();
                Championnat retourpage = new Championnat();
                try {
                    retourpage.start(new Stage());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }});

        Button retour = new Button("Retour");
        retour.setStyle("-fx-background-color: #0903da; -fx-text-fill: white; -fx-font-weight: bold; -fx-min-width: 100px; -fx-min-height: 40px; -fx-shape: \"M 15 0 L 85 0 Q 100 0, 100 15 L 100 85 Q 100 100, 85 100 L 15 100 Q 0 100, 0 85 L 0 15 Q 0 0, 15 0 Z\";");
        retour.setOnMouseEntered(e -> retour.setStyle("-fx-background-color: #020938; -fx-text-fill: white; -fx-font-weight: bold; -fx-min-width: 100px; -fx-min-height: 40px; -fx-shape: \"M 15 0 L 85 0 Q 100 0, 100 15 L 100 85 Q 100 100, 85 100 L 15 100 Q 0 100, 0 85 L 0 15 Q 0 0, 15 0 Z\";"));
        retour.setOnMouseExited(e -> retour.setStyle("-fx-background-color: #0903da; -fx-text-fill: white; -fx-font-weight: bold; -fx-min-width: 100px; -fx-min-height: 40px; -fx-shape: \"M 15 0 L 85 0 Q 100 0, 100 15 L 100 85 Q 100 100, 85 100 L 15 100 Q 0 100, 0 85 L 0 15 Q 0 0, 15 0 Z\";"));
        retour.setOnAction(event -> {
            Championnat retourpage = new Championnat();
            primaryStage.close();
            try {
                retourpage.start(new Stage());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        boutonConfirmer.setStyle("-fx-background-color: #2c8c07; -fx-text-fill: white; -fx-font-weight: bold; -fx-min-width: 100px; -fx-min-height: 40px; -fx-shape: \"M 15 0 L 85 0 Q 100 0, 100 15 L 100 85 Q 100 100, 85 100 L 15 100 Q 0 100, 0 85 L 0 15 Q 0 0, 15 0 Z\";");
        boutonConfirmer.setOnMouseEntered(e -> boutonConfirmer.setStyle("-fx-background-color: #144401; -fx-text-fill: white; -fx-font-weight: bold; -fx-min-width: 100px; -fx-min-height: 40px; -fx-shape: \"M 15 0 L 85 0 Q 100 0, 100 15 L 100 85 Q 100 100, 85 100 L 15 100 Q 0 100, 0 85 L 0 15 Q 0 0, 15 0 Z\";"));
        boutonConfirmer.setOnMouseExited(e -> boutonConfirmer.setStyle("-fx-background-color: #2c8c07; -fx-text-fill: white; -fx-font-weight: bold; -fx-min-width: 100px; -fx-min-height: 40px; -fx-shape: \"M 15 0 L 85 0 Q 100 0, 100 15 L 100 85 Q 100 100, 85 100 L 15 100 Q 0 100, 0 85 L 0 15 Q 0 0, 15 0 Z\";"));

        boutonConfirmer.setOnAction(event -> {
            Championnat retourpage = new Championnat();
            primaryStage.close();
            String nomChampionnat = nom.getText();
            String typeChampionnat = typeComboBox.getValue();
            insert(nomChampionnat, typeChampionnat);
            try {
                retourpage.start(new Stage());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        VBox root = new VBox();
        HBox titleBox = new HBox(titleLabel); // Créer une HBox pour le titre
        root.setBackground(new Background(new BackgroundImage(
                new Image(new File("C:\\Users\\Administrator\\Desktop\\javafx\\1014946_6272.jpg").toURI().toString(), true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
        titleBox.setStyle("-fx-alignment: center;"); // Centrer le titre horizontalement
        HBox buttonsBox = new HBox(boutonConfirmer,retour);
        VBox.setMargin(buttonsBox, new Insets(0, 0, 10, 30));
        buttonsBox.setAlignment(Pos.CENTER_LEFT);
        buttonsBox.setSpacing(350);
        root.getChildren().addAll(titleBox, emptyLabel1, emptyLabel2, emptyLabel3, labelNomChampionnat, emptyLabel4, nom , emptyLabel5, labelType ,  emptyLabel12,typeComboBox ,emptyLabel6,  emptyLabel7,emptyLabel8 , emptyLabel9 , emptyLabel10, emptyLabel11 , buttonsBox);
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ajouter un championnat");
        primaryStage.show();
    }
    private void insert(String nomChampionnat, String textFieldType) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetjavafx", "root", "");
            // Obtenir la date système
            Date dateSysteme = Date.valueOf(LocalDate.now());
            String sql = "INSERT INTO championship (championship_name, date, type) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nomChampionnat);
            statement.setDate(2, dateSysteme); // Insérer la date système
            statement.setString(3, textFieldType);
            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}