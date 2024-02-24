module Championnat {
requires  java.sql;
    requires javafx.fxml;
    requires javafx.controls;
    requires transitive javafx.base;
    requires transitive javafx.graphics;
    exports authentificationetajoututilisateur to javafx.graphics , javafx.fxml , javafx.controls , javafx.base;
    opens authentificationetajoututilisateur to javafx.fxml , javafx.graphics, javafx.controls , javafx.base;
    exports Championnat ;
    opens Championnat  ;
}

