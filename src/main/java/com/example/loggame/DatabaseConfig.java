package com.example.loggame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class DatabaseConfig {

    private static final Logger logger = LogManager.getLogger(DatabaseConfig.class);
    private static Connection connection = null;

    public static synchronized Connection getConnection() throws SQLException, IOException {
        if (connection != null && !connection.isClosed()) {
            return connection;
        }

        Properties prop = new Properties();
        try (InputStream inputStream = DatabaseConfig.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (inputStream == null) {
                throw new IOException("db.properties not found on classpath");
            }
            prop.load(inputStream);
        }

        String url = prop.getProperty("db.url");
        String user = prop.getProperty("db.user");
        String password = prop.getProperty("db.password");

        logger.info("Attempting to connect to the database...");
        connection = DriverManager.getConnection(url, user, password);
        logger.info("Database connection successful.");

        ensureSchema(connection);
        return connection;
    }

    private static void ensureSchema(Connection conn) throws SQLException {
        String ddl = "CREATE TABLE IF NOT EXISTS users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "username VARCHAR(50) UNIQUE NOT NULL, " +
                "password_hash VARCHAR(255) NOT NULL" +
                ")";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(ddl);
            logger.info("Verified that the 'users' table exists.");
        }
    }
}
