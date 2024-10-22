package com.anroypaul.javasockethttpserver;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class SimpleHTTPServer {

    private Map<String, Resource> allowedResources;
    private static final int THREAD_POOL_SIZE = 10;
    private static final int PORT = 8080;

    private static Logger getLogger() {
        return LogManager.getLogger(SimpleHTTPServer.class);
    }
    private ServerSocket server;
    private ExecutorService threadPool;

    SimpleHTTPServer() throws IOException {
        this.server = new ServerSocket(PORT);
        this.threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        this.allowedResources = new HashMap<>();
    }

    public void startServer() {
        // Create a thread pool with a fixed number of threads
        try {
            getLogger().debug("Server started on port " + PORT);

            // Continuously listen for incoming connections
            while (true) {
                // Accept a client connection
                Socket clientSocket = this.server.accept();

                // Submit the client handler task to the thread pool
                this.threadPool.submit(new ClientHandler(clientSocket));

                if(this.threadPool.isTerminated()) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Shutdown the thread pool when the server stops
            this.threadPool.shutdown();
        }
    }

    public void addResource(String path, Resource resource) {
        this.allowedResources.put(path, resource);
    }
}
