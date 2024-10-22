package com.anroypaul.javasockethttpserver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class SimpleHTTPServerApplication {

    private static final Logger LOGGER = LogManager.getLogger(SimpleHTTPServerApplication.class);

    public static void main(String[] args) throws IOException {
        LOGGER.info("Application started");

        SimpleHTTPServer simpleHTTPServer = new SimpleHTTPServer();

        //simpleHTTPServer.addResource(String path, );

        simpleHTTPServer.startServer();
    }
}
