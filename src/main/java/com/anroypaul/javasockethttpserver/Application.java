package com.anroypaul.javasockethttpserver;

import com.anroypaul.javasockethttpserver.database.Connection;
import com.anroypaul.javasockethttpserver.database.factory.ConnectionFactory;
import com.anroypaul.javasockethttpserver.server.SimpleServer;
import com.anroypaul.javasockethttpserver.server.SimpleServerImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;

public class Application {

    private final ConnectionFactory connectionFactory;
    private static final Logger LOGGER = LogManager.getLogger(Application.class.getName());


    public Application(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void start() throws IOException, SQLException {
        Connection connection = connectionFactory.getConnection();
        connection.connect();

        // start server
        SimpleServer server = new SimpleServerImpl();
    }
}
