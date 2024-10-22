package com.anroypaul.javasockethttpserver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ClientHandler implements Runnable {
    private final Logger LOGGER = LogManager.getLogger(ClientHandler.class);

    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))
        ) {
            String requestLine = in.readLine();
            if (requestLine != null && !requestLine.isEmpty()) {
                LOGGER.debug("Received request: " + requestLine);

                String[] requestParts = requestLine.split(" ");
                String method = requestParts[0];
                String resource = requestParts[1];

                // Step 3: Read and parse headers
                Map<String, String> headers = new HashMap<>();
                String line;
                int contentLength = 0;
                while (!(line = in.readLine()).isEmpty()) {
                    String[] headerParts = line.split(": ");
                    headers.put(headerParts[0], headerParts[1]);
                    System.out.println(Arrays.toString(headerParts));
                    // Capture the Content-Length header to know the body length
                    if (headerParts[0].equalsIgnoreCase("Content-Length")) {
                        contentLength = Integer.parseInt(headerParts[1]);
                    }
                }

                // TODO check resource if exist
                if (method.equals("GET")) {
                    handleGetRequest(out, resource);
                } else if (method.equals("POST")) {
                    handlePostRequest(requestLine, contentLength, in, out);
                } else {
                    handleMethodNotSupported(out);
                }

                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void handlePostRequest(String requestLine, int contentLength, BufferedReader in, BufferedWriter out) throws IOException {
        // handlePostMethod
        System.out.println(requestLine);
        char[] body = new char[contentLength];
        in.read(body, 0, contentLength);
        String requestBody = new String(body);
        System.out.println("POST body: " + requestBody);

        // Step 5: Send response back to client
        String responseBody = "<html><body><h1>POST request received!</h1><p>Body: " + requestBody + "</p></body></html>";

        out.write("HTTP/1.1 200 OK\r\n");
        out.write("Date: " + new Date() + "\r\n");
        out.write("Content-Type: text/html\r\n");
        out.write("Content-Length: " + responseBody.length() + "\r\n");
        out.write("\r\n");
        out.write(responseBody);
    }

    private static void handleMethodNotSupported(BufferedWriter out) throws IOException {
        out.write("HTTP/1.1 405 Method Not Allowed\r\n");
        out.write("Allow: GET\r\n");
        out.write("\r\n");
    }

    private void handleGetRequest(BufferedWriter out, String resource) throws IOException {
        // TODO add HTML wrapper
        // TODO add search in database for requested id (if params presented) return hello world if no params
        LOGGER.debug(resource);
        Map<String, String> params = new HashMap<>();
        String[] paramsString = resource.split("\\?");
        if(paramsString.length > 1) {

        }
        // TODO get key value form resourse
        String responseBody = "<html><body><h1>Welcome to the HTTP Server!</h1></body></html>";
        if (resource.equals("/hello")) {
            responseBody = "<html><body><h1>Hello, World!</h1></body></html>";
        }

        out.write("HTTP/1.1 200 OK\r\n");
        out.write("Date: " + new Date() + "\r\n");
        out.write("Content-Type: text/html\r\n");
        out.write("Content-Length: " + responseBody.length() + "\r\n");
        out.write("\r\n");
        out.write(responseBody);
    }
}