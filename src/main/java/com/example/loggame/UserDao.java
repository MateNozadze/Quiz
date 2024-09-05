package com.example.loggame;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDao {

    private Properties properties;
    private static final Logger logger = Logger.getLogger(UserDao.class.getName());

    public UserDao() {
        loadDatabaseProperties();
    }

    private void loadDatabaseProperties() {
        properties = new Properties();
        try (InputStream input = getClass().getResourceAsStream("/db.properties")) {
            if (input == null) {
                logger.severe("Sorry, unable to find db.properties");
                return;
            }
            properties.load(input);
            logger.info("Database properties loaded successfully.");
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error loading database properties", ex);
        }
    }

    public User authenticate(String username, String password) {
        String url = properties.getProperty("db.url");
        String dbUsername = properties.getProperty("db.username");
        String dbPassword = properties.getProperty("db.password");

        logger.info("Attempting to authenticate user: " + username);

        String sql = "SELECT id, username, password FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String dbUser = rs.getString("username");
                    String dbPass = rs.getString("password");
                    logger.info("User authenticated successfully: " + username);
                    return new User(id, dbUser, dbPass);
                } else {
                    logger.warning("Authentication failed for user: " + username);
                    return null;
                }
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Database error during authentication", ex);
            return null;
        }
    }
}
