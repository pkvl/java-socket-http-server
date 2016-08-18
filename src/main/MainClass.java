package main;

import java.sql.Statement;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainClass {

    public static void main(String[] args) throws IOException, URISyntaxException, SQLException {
        // TODO Auto-generated method stub
        HttpRequest hr = new HttpRequest();
        hr.startServer();
    }
}
