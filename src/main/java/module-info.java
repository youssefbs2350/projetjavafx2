module authetifcationetajoututilisateur {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens authentificationetajoututilisateur to javafx.fxml;
    exports authentificationetajoututilisateur;
    exports detailsMatch;
}