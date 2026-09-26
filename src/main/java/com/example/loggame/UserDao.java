package com.example.loggame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDao {

    private static final Logger logger = LogManager.getLogger(UserDao.class);

    public enum RegistrationResult {
        SUCCESS,
        USERNAME_TAKEN,
        ERROR
    }

    public User authenticate(String username, String password) {
        String sql = "SELECT id, username, password_hash FROM users WHERE username = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password_hash");
                    if (PasswordUtil.verify(password, storedHash)) {
                        logger.info("User authenticated successfully: {}", username);
                        return new User(rs.getInt("id"), rs.getString("username"));
                    }
                }
            }
            logger.warn("Authentication failed for user: {}", username);
            return null;
        } catch (SQLException | IOException ex) {
            logger.error("Database error during authentication", ex);
            return null;
        }
    }

    public RegistrationResult register(String username, String password) {
        String checkSql = "SELECT 1 FROM users WHERE username = ?";
        String insertSql = "INSERT INTO users (username, password_hash) VALUES (?, ?)";

        try (Connection conn = DatabaseConfig.getConnection()) {
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setString(1, username);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {
                        logger.warn("Registration rejected, username already taken: {}", username);
                        return RegistrationResult.USERNAME_TAKEN;
                    }
                }
            }

            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, username);
                insertStmt.setString(2, PasswordUtil.hash(password));
                insertStmt.executeUpdate();
            }

            logger.info("Registered new user: {}", username);
            return RegistrationResult.SUCCESS;
        } catch (SQLException | IOException ex) {
            logger.error("Database error during registration", ex);
            return RegistrationResult.ERROR;
        }
    }
}
