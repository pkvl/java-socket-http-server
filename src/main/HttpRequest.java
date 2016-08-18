package main;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class HttpRequest {

    final String GET = "GET";
    final String POST = "POST";
    final int PORT = 1234;

    private String httpResponse;
    private ServerSocket server;

    private String method;
    private String symbols;

    public HttpRequest() throws IOException {
        this.server = new ServerSocket(PORT);
    }

    public void startServer() throws IOException, SQLException {
        //server = new ServerSocket(PORT);

        System.out.println("Listening for connection on port " + PORT + "...");

        while (true) {
            try (Socket socket = server.accept()) {
                // Initialize reader and writer
                try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    try (PrintWriter out = new PrintWriter(socket.getOutputStream())) {
                        // Start sending reply, using the HTTP 1.1 protocol
                        out.print("HTTP/1.1 200 \r\n");
                        out.print("Content-Type: text/plain\r\n");
                        out.print("Connection: close\r\n");
                        out.print("\r\n");

                        // Read HTTP request from the client
                        // Get HttpRequest lines
                        String parts[] = getRequest(in, out);

                        int id = 0;
                        //String httpResponse = null;

                        try {
                            method = parts[0];
                            method = method.substring(0, method.length() - 1); // Remove space
                            //System.out.println(method + " " + symbols);

                            // Return HTTP response with info about user, stored in DB
                            if (GET.equals(method)) {
                                symbols = parts[1];
                                parts = symbols.split(" ");

                                // Check if ID is integer
                                if (ParseChecker.tryParseInt(parts[0]))
                                    id = new Integer(parts[0]);
                                setHttpResponse(new DbWork().getUserInfo(id));
                            } else if (POST.equals(method)) {
                                symbols = parts[1];
                                if (ParseChecker.tryParseInt(parts[1]))
                                    id = new Integer(parts[1]);
                                String name = parts[2];
                                String surname = parts[3];
                                setHttpResponse(new DbWork().addNewUser(new User(id, name, surname)));
                            }
                            //System.out.println(id);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid id number");
                        }

                        out.print(getHttpResponse()); // Print info about user
                        out.close(); // Flush and close the output stream
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //in.close(); // Close the input stream
            }
        }
    }

    private String[] getRequest(BufferedReader in, PrintWriter out) throws IOException {
        String line;
        String[] parts = null;
        while ((line = in.readLine()) != null && line.length() != 0) {
            if (parts == null)
                parts = line.split("/");

            out.print(line + "\r\n");
        }
        return parts;
    }

    public String getHttpResponse() {
        return httpResponse;
    }

    public void setHttpResponse(String httpResponse) {
        this.httpResponse = httpResponse;
    }
}
