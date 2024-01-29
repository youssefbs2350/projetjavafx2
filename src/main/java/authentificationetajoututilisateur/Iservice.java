package authentificationetajoututilisateur;

import java.sql.SQLException;
import java.util.List;

public interface Iservice <T>{

    void ajouter(T t) throws SQLException;

    boolean supprimer(T t) throws SQLException;

    boolean update(T t) throws SQLException;

    T findById(T t) throws SQLException;

    List<T> readAll() throws SQLException;


}