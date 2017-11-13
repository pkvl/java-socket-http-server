package com.anroypaul.javasockethttpserver;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:tcp://localhost/~/test";

    //  Database credentials
    private static final String USER = "sa";
    private static final String PASS = "";

    private static Connection conn;

    private static final Logger LOG = LogManager.getLogger(ConnectionManager.class);

    public static Connection getConnection() {
        try {
            Class.forName(JDBC_DRIVER);
            try {
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
//                System.out.println("Connection established successfully");
                LOG.info("Connection established successfully");
            } catch (SQLException ex) {
                // log an exception. fro example:
//                System.out.println("Failed to create the database connection.");
                LOG.error("Failed to create the database connection.");
            }
        } catch (ClassNotFoundException ex) {
            // log an exception. for example:
//            System.out.println("Driver not found.");
            LOG.error("Driver not found.");
        }
        return conn;
    }
}
