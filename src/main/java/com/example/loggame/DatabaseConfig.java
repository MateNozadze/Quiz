package com.example.loggame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConfig {
    private static final Logger logger = LogManager.getLogger(DatabaseConfig.class);
    private static Connection connection = null;

    public static Connection getConnection() throws SQLException, IOException {
        if (connection != null && !connection.isClosed()) {
            return connection;
        } else {
            try {
                Properties prop = new Properties();
                InputStream inputStream = DatabaseConfig.class.getClassLoader().getResourceAsStream("db.properties");
                prop.load(inputStream);
                String url = prop.getProperty("db.url");
                String user = prop.getProperty("db.user");
                String password = prop.getProperty("db.password");

                logger.info("Attempting to connect to the database...");
                connection = DriverManager.getConnection(url, user, password);
                logger.info("Database connection successful.");
                return connection;
            } catch (SQLException | IOException e) {
                logger.error("Error getting database connection: ", e);
                throw e;
            }
        }
    }
}
