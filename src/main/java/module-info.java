module authetifcationetajoututilisateur {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires rt;
    requires jfxrt;


    opens authentificationetajoututilisateur to javafx.fxml;
    exports authentificationetajoututilisateur;
}