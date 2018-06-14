package com.anroypaul.javasockethttpserver.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.DriverManager;
import java.sql.SQLException;

public class H2DatabaseConnection implements Connection {

    // JDBC driver name and database URL
    // todo put in config file
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:tcp://localhost/~/test";

    //  Database credentials
    // todo put in config file
    private static final String USER = "sa";
    private static final String PASS = "";

    private java.sql.Connection conn;

    private final Logger LOGGER = LogManager.getLogger(H2DatabaseConnection.class);

    @Override
    public void connect() {
        try {
            Class.forName(JDBC_DRIVER);
            try {
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
                LOGGER.info("Connection established successfully");
            } catch (SQLException ex) {
                LOGGER.error("Failed to create the database connection.");
            }
        } catch (ClassNotFoundException ex) {
            LOGGER.error("Driver not found.");
        }
    }
}