package com.anroypaul.javasockethttpserver;

import com.anroypaul.javasockethttpserver.database.factory.ConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

public class SocketHttpServer {

    private static final Logger LOGGER = LogManager.getLogger(SocketHttpServer.class);

    public static void main(String[] args) throws IOException, URISyntaxException, SQLException {
        ConnectionManager.DatabaseType databaseType = ConnectionManager.DatabaseType.H2;
        ConnectionFactory connectionFactory = ConnectionManager.getConnectionFactory(databaseType);

        Application application = new Application(connectionFactory);
        application.start();

        LOGGER.info("Application started!");
    }
}
