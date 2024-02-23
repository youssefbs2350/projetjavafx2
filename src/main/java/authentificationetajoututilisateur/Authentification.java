package authentificationetajoututilisateur;

import java.sql.*;

public class Authentification {
    private static Authentification instance;
    private Connection connection;

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/projetjavafx";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    private Authentification() throws SQLException {
        try {
            connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Authentification getInstance() throws SQLException {
        if (instance == null) {
            instance = new Authentification();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean authenticate(String username, String password) {
        try {
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
    private boolean validateInput(String username, String password) {
        return !username.isEmpty() && !password.isEmpty();
    }


    public String getUsername(String username, String password) {
        try {
            String query = "SELECT username FROM utilisateurs WHERE username = ? AND password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("username");
                    } else {
                        return null; // L'utilisateur n'a pas été trouvé
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

   /* public String getCurrentUsername() {
        String username = null;
        try {
            // Établir la connexion à la base de données (à remplacer par votre logique de connexion)
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/votre_base_de_donnees", "votre_utilisateur", "votre_mot_de_passe");

            // Exécuter la requête pour obtenir le nom d'utilisateur actuel
            String query = "SELECT username FROM utilisateurs WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);// Obtenez l'ID de l'utilisateur actuel (à remplacer par votre logique)
            ResultSet resultSet = preparedStatement.executeQuery();

            // Vérifier si un résultat a été retourné
            if (resultSet.next()) {
                username = resultSet.getString("username");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Fermer la connexion (à remplacer par votre logique de gestion de la connexion)
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return username;
    }*/
    }



