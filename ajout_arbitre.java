package com.example.ajout_arbitre;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;

public class ajout_arbitre extends Application {

    private TableView<Referee> tableView;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetfxv2", "root", "");

            Label titleLabel = new Label("Ajout d'un arbitre");
            titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

            Label labelRefereeName = new Label("Nom de l'arbitre :");
            labelRefereeName.setStyle("-fx-font-weight: bold;");
            TextField textFieldRefereeName = new TextField();

            Label labelMatchesArbitrated = new Label("Matches arbitrés :");
            labelMatchesArbitrated.setStyle("-fx-font-weight: bold;");
            TextField textFieldMatchesArbitrated = new TextField();

            Label labelTelephoneNumber = new Label("Numéro de téléphone :");
            labelTelephoneNumber.setStyle("-fx-font-weight: bold;");
            TextField textFieldTelephoneNumber = new TextField();

            Button boutonConfirmer = new Button("Confirmer");
            boutonConfirmer.setStyle("-fx-background-color: #7DBC22; -fx-text-fill: white; -fx-font-weight: bold;");

            Button boutonListeArbitres = new Button("Liste des arbitres");
            boutonListeArbitres.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold;");

            VBox root = new VBox();
            HBox titleBox = new HBox(titleLabel);
            titleBox.setStyle("-fx-alignment: center;");

            HBox buttonsBox = new HBox(boutonConfirmer, boutonListeArbitres);
            VBox.setMargin(buttonsBox, new Insets(0, 0, 10, 30));
            buttonsBox.setAlignment(Pos.CENTER_LEFT);

            boutonConfirmer.setOnAction(event -> {
                String refereeName = textFieldRefereeName.getText();
                int matchesArbitrated = Integer.parseInt(textFieldMatchesArbitrated.getText());
                String telephoneNumber = textFieldTelephoneNumber.getText();

                if (isPhoneNumberValid(telephoneNumber)) {
                    insererArbitreDansLaBaseDeDonnees(refereeName, matchesArbitrated, telephoneNumber);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("Le numéro de téléphone doit avoir 8 caractères et ne contenir que des chiffres.");
                    alert.showAndWait();
                }
            });

            boutonListeArbitres.setOnAction(event -> {
                afficherListeArbitres();
            });

            root.getChildren().addAll(titleBox, labelRefereeName, textFieldRefereeName,
                    labelMatchesArbitrated, textFieldMatchesArbitrated, labelTelephoneNumber, textFieldTelephoneNumber, buttonsBox);

            Scene scene = new Scene(root, 720, 600);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Ajouter un arbitre");
            primaryStage.show();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isPhoneNumberValid(String telephoneNumber) {
        return telephoneNumber.matches("\\d{8}");
    }


    private void insererArbitreDansLaBaseDeDonnees(String refereeName, int matchesArbitrated, String telephoneNumber) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetfxv2", "root", "")) {
            String sql = "INSERT INTO referee (referee_name, matches_arbitrated, telephone_number) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, refereeName);
                statement.setInt(2, matchesArbitrated);
                statement.setString(3, telephoneNumber);
                statement.executeUpdate();

                // Récupérer l'ID auto-généré
                ResultSet generatedKeys = statement.getGeneratedKeys();
                int generatedId = 0;
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1);
                    System.out.println("Arbitre ajouté avec l'ID généré : " + generatedId);
                }

                refreshTable(); // Rafraîchir le tableau après l'insertion

                // Mettre à jour matches_arbitrated dans la table referee en fonction de la table match
                updateMatchesArbitrated(generatedId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateMatchesArbitrated(int refereeId) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetfxv2", "root", "")) {
            // Compter le nombre de matches pour le refereeId donné dans la table des matches
            String countMatchesSql = "SELECT COUNT(*) AS matchCount FROM matches WHERE referee_id = ?";
            try (PreparedStatement countMatchesStatement = connection.prepareStatement(countMatchesSql)) {
                countMatchesStatement.setInt(1, refereeId);
                ResultSet countMatchesResultSet = countMatchesStatement.executeQuery();

                if (countMatchesResultSet.next()) {
                    int matchCount = countMatchesResultSet.getInt("matchCount");

                    // Mettre à jour matches_arbitrated dans la table des arbitres
                    String updateRefereeSql = "UPDATE referee SET matches_arbitrated = ? WHERE referee_id = ?";
                    try (PreparedStatement updateRefereeStatement = connection.prepareStatement(updateRefereeSql)) {
                        updateRefereeStatement.setInt(1, matchCount);
                        updateRefereeStatement.setInt(2, refereeId);
                        updateRefereeStatement.executeUpdate();
                        System.out.println("Matches arbitrated mis à jour pour l'arbitre avec l'ID : " + refereeId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private void afficherListeArbitres() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetfxv2", "root", "")) {
            String sql = "SELECT * FROM referee";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                ResultSet resultSet = statement.executeQuery();

                ObservableList<Referee> refereeList = FXCollections.observableArrayList();

                while (resultSet.next()) {
                    int refereeId = resultSet.getInt("referee_id");
                    String refereeName = resultSet.getString("referee_name");
                    int matchesArbitrated = resultSet.getInt("matches_arbitrated");
                    String telephoneNumber = resultSet.getString("telephone_number");

                    refereeList.add(new Referee(refereeId, refereeName, matchesArbitrated, telephoneNumber));
                }

                // Créer une nouvelle fenêtre pour afficher le tableau
                Stage tableViewStage = new Stage();
                tableViewStage.setTitle("Liste des arbitres");

                tableView = new TableView<>();
                tableView.setItems(refereeList);

                TableColumn<Referee, Integer> idColumn = new TableColumn<>("ID");
                idColumn.setCellValueFactory(new PropertyValueFactory<>("refereeId"));

                TableColumn<Referee, String> nameColumn = new TableColumn<>("Nom");
                nameColumn.setCellValueFactory(new PropertyValueFactory<>("refereeName"));

                TableColumn<Referee, Integer> matchesColumn = new TableColumn<>("Matches Arbitrés");
                matchesColumn.setCellValueFactory(new PropertyValueFactory<>("matchesArbitrated"));

                TableColumn<Referee, String> telephoneColumn = new TableColumn<>("Numéro de téléphone");
                telephoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephoneNumber"));

                TableColumn<Referee, Void> deleteColumn = new TableColumn<>("Supprimer");
                deleteColumn.setCellFactory(param -> new TableCell<>() {
                    private final Button deleteButton = new Button("Supprimer");

                    {
                        deleteButton.setOnAction(event -> {
                            Referee referee = getTableView().getItems().get(getIndex());
                            supprimerArbitre(referee.getRefereeId());
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(deleteButton);
                        }
                    }
                });

                TableColumn<Referee, Void> editColumn = new TableColumn<>("Modifier");
                editColumn.setCellFactory(param -> new TableCell<>() {
                    private final Button editButton = new Button("Modifier");

                    {
                        editButton.setOnAction(event -> {
                            Referee referee = getTableView().getItems().get(getIndex());
                            modifierArbitre(referee);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(editButton);
                        }
                    }
                });

                tableView.getColumns().addAll(idColumn, nameColumn, matchesColumn, telephoneColumn, deleteColumn, editColumn);

                Scene tableViewScene = new Scene(new VBox(tableView), 700, 500);
                tableViewStage.setScene(tableViewScene);
                tableViewStage.show();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void refreshTable() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetfxv2", "root", "")) {
            String sql = "SELECT * FROM referee";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                ResultSet resultSet = statement.executeQuery();

                ObservableList<Referee> refereeList = FXCollections.observableArrayList();

                while (resultSet.next()) {
                    int refereeId = resultSet.getInt("referee_id");
                    String refereeName = resultSet.getString("referee_name");
                    int matchesArbitrated = resultSet.getInt("matches_arbitrated");
                    String telephoneNumber = resultSet.getString("telephone_number");

                    refereeList.add(new Referee(refereeId, refereeName, matchesArbitrated, telephoneNumber));
                }

                tableView.setItems(refereeList); // Mettre à jour le TableView avec les dernières données

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void supprimerArbitre(int refereeId) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetfxv2", "root", "")) {
            String sql = "DELETE FROM referee WHERE referee_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, refereeId);
                statement.executeUpdate();
                System.out.println("Arbitre supprimé avec l'ID : " + refereeId);

                refreshTable(); // Rafraîchir le tableau après la suppression
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void modifierArbitre(Referee referee) {
        Stage modifierStage = new Stage();
        VBox modifierVBox = new VBox();
        modifierVBox.setSpacing(10);
        modifierVBox.setPadding(new Insets(10));

        Label modifierLabel = new Label("Modifier l'arbitre");
        modifierLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField textFieldRefereeName = new TextField(referee.getRefereeName());
        TextField textFieldMatchesArbitrated = new TextField(String.valueOf(referee.getMatchesArbitrated()));
        TextField textFieldTelephoneNumber = new TextField(referee.getTelephoneNumber());

        Button boutonModifier = new Button("Modifier");
        boutonModifier.setStyle("-fx-background-color: #7DBC22; -fx-text-fill: white; -fx-font-weight: bold;");

        modifierVBox.getChildren().addAll(modifierLabel, textFieldRefereeName, textFieldMatchesArbitrated, textFieldTelephoneNumber, boutonModifier);
        Scene modifierScene = new Scene(modifierVBox, 300, 200);

        modifierStage.setScene(modifierScene);
        modifierStage.setTitle("Modifier l'arbitre");
        modifierStage.show();

        boutonModifier.setOnAction(event -> {
            String newRefereeName = textFieldRefereeName.getText();
            int newMatchesArbitrated = Integer.parseInt(textFieldMatchesArbitrated.getText());
            String newTelephoneNumber = textFieldTelephoneNumber.getText();

            modifierArbitreDansLaBaseDeDonnees(referee.getRefereeId(), newRefereeName, newMatchesArbitrated, newTelephoneNumber);
            modifierStage.close();
        });
    }

    private void modifierArbitreDansLaBaseDeDonnees(int refereeId, String newRefereeName, int newMatchesArbitrated, String newTelephoneNumber) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetfxv2", "root", "")) {
            String sql = "UPDATE referee SET referee_name = ?, matches_arbitrated = ?, telephone_number = ? WHERE referee_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, newRefereeName);
                statement.setInt(2, newMatchesArbitrated);
                statement.setString(3, newTelephoneNumber);
                statement.setInt(4, refereeId);
                statement.executeUpdate();
                System.out.println("Arbitre modifié avec l'ID : " + refereeId);

                refreshTable(); // Rafraîchir le tableau après la modification
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void confirmerAction(ActionEvent actionEvent) {
    }

    public void listeArbitresAction(ActionEvent actionEvent) {
    }

    public static class Referee {
        private final int refereeId;
        private final String refereeName;
        private final int matchesArbitrated;
        private final String telephoneNumber;

        public Referee(int refereeId, String refereeName, int matchesArbitrated, String telephoneNumber) {
            this.refereeId = refereeId;
            this.refereeName = refereeName;
            this.matchesArbitrated = matchesArbitrated;
            this.telephoneNumber = telephoneNumber;
        }

        public int getRefereeId() {
            return refereeId;
        }

        public String getRefereeName() {
            return refereeName;
        }

        public int getMatchesArbitrated() {
            return matchesArbitrated;
        }

        public String getTelephoneNumber() {
            return telephoneNumber;
        }
    }
}
