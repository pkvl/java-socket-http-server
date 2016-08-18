package main;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

public class MainClass {

    public static void main(String[] args) throws IOException, URISyntaxException, SQLException {
        HttpRequest hr = new HttpRequest();
        hr.startServer();
    }
}
