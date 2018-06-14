package com.anroypaul.javasockethttpserver;

import com.anroypaul.javasockethttpserver.database.Connection;
import com.anroypaul.javasockethttpserver.database.factory.ConnectionFactory;

import java.io.IOException;
import java.sql.SQLException;

public class Application {

    private final ConnectionFactory connectionFactory;

    public Application(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void start() throws IOException, SQLException {
        Connection connection = connectionFactory.getConnection();
        connection.connect();

        // start server
        new SimpleServer().startServer();
    }
}
