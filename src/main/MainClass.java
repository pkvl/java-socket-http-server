package main;

import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

public class MainClass {

    private static final Logger logger = LogManager.getLogger(MainClass.class);

    public static void main(String[] args) throws IOException, URISyntaxException, SQLException {
        logger.trace("Application started");
        SocketServer hr = new SocketServer();
        hr.startServer();
    }
}
