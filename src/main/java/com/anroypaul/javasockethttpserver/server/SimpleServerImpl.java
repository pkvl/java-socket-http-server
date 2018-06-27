package com.anroypaul.javasockethttpserver.server;

import com.anroypaul.javasockethttpserver.dao.UserDAO;
import com.anroypaul.javasockethttpserver.domain.User;
import com.anroypaul.javasockethttpserver.helpers.ParseHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.StringTokenizer;

public class SimpleServerImpl implements SimpleServer {

    private SimpleRequest simpleRequest;
    private SimpleResponse simpleResponse;

    private final String GET = "GET";
    private final String POST = "POST";
    private final int PORT = 1234;

    private final Logger LOGGER = LogManager.getLogger(SimpleServerImpl.class);
    private String response;
    private ServerSocket server;

    private String method;
    private String symbols;

    public SimpleServerImpl() throws IOException {
        this.server = new ServerSocket(PORT);
        this.start();
    }

    private void start() throws IOException {
        LOGGER.debug("Listening for connection on port " + PORT + "...");

        while (true) {
            try (Socket socket = server.accept();
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream())) {
                LOGGER.info("Client connected");
                printReply(out);

                // Read HTTP request from the client
                // Get Request lines
                String parts[] = getRequest(in, out);
//                requestHandler(parts);
                out.print(getResponse());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void requestHandler(String[] parts) throws SQLException {
        int id = 0;

        try {
            method = ParseHelper.extractIndex(ParseHelper.removeSpaces(parts[0]), 0); // Remove space
            LOGGER.info("Method is " + method);

            switch (method) {
                // Set HTTP response with info about user, stored in DB
                case GET:
                    parts = ParseHelper.removeSpaces(ParseHelper.extractIndex(parts, 1));
                    // Check if ID is integer
                    if (ParseHelper.isStringInt(parts[0]))
                        id = Integer.valueOf(parts[0]);
//                    setResponse(new UserDAO().getInfoById(id));
                    break;
                // Set HTTP response with adding info about user to DB
                case POST:
                    symbols = parts[1];
                    if (ParseHelper.isStringInt(parts[1]))
                        id = Integer.valueOf(parts[1]);
                    String name = parts[2];
                    String surname = ParseHelper.extractIndex(ParseHelper.removeSpaces(parts, 3), 0);
//                    setResponse(new UserDAO().add(new User(id, name, surname)));
                    break;
                default:
                    LOGGER.error("Wrong REST-method");
                    break;
            }
        } catch (NumberFormatException e) {
            LOGGER.error("Invalid id number");
        }
    }

    private void printReply(PrintWriter out) {
        // Start sending reply, using the HTTP 1.1 protocol
        out.print("HTTP/1.1 200 \r\n");
        out.print("Content-Type: text/plain\r\n");
        out.print("Connection: close\r\n");
        out.print("\r\n");
    }

    private String[] getRequest(BufferedReader in, PrintWriter out) throws IOException {
        String line;
        String[] parts = null;
        boolean checkNextLine = false;
        while ((line = in.readLine()) != null) {
            out.print(line + "\r\n");
            System.out.print(line + "\r\n");

//            if (checkNextLine) {
//                if (line.isEmpty()) {
//                    break;
//                }
//            }
//
//            if (line.isEmpty()) {
//                checkNextLine = true;
//            }
        }
//        while (!(line = in.readLine().trim()).isEmpty()) {
//            if (parts == null)
//                parts = line.split("/");
//
//            out.print(line + "\r\n");
//            System.out.print(line + "\r\n");
//        }
        return parts;
    }

    private String getResponse() {
        return response;
    }

    private void setResponse(String response) {
        this.response = response;
    }

    public void requestHandler() {
    }

    public void responseHandler() {
    }
}
