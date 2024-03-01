module authetifcationetajoututilisateur {
  /*  requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens authentificationetajoututilisateur to javafx.fxml;
    exports authentificationetajoututilisateur;
}*/
requires  java.sql;
    requires javafx.fxml;
    requires javafx.controls;
    requires transitive javafx.base;
    requires transitive javafx.graphics;
    requires javafx.media;
    exports authentificationetajoututilisateur to javafx.graphics , javafx.fxml , javafx.controls , javafx.base , javafx.media;
    opens authentificationetajoututilisateur to javafx.fxml , javafx.graphics, javafx.controls , javafx.base, javafx.media;
    exports Teams;
    opens Teams to javafx.fxml;
}