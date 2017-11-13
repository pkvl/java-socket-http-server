package com.anroypaul.javasockethttpserver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

public class SocketHttpServer {

    private static final Logger LOGGER = LogManager.getLogger(SocketHttpServer.class);

    public static void main(String[] args) throws IOException, URISyntaxException, SQLException {
        LOGGER.info("Application started");
        SimpleServer simpleServer = new SimpleServer();
        simpleServer.startServer();
    }
}
