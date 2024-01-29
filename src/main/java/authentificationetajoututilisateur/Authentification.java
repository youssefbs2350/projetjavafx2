package authentificationetajoututilisateur;

import java.sql.*;

public class Authentification {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/projetjavafx";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public boolean authenticate(String username, String password) {

        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM utilisateurs WHERE username = ? AND password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
    private boolean validateInput (String username, String password) {
        return !username.isEmpty() && !password.isEmpty();
    }
}
