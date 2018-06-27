package com.anroypaul.javasockethttpserver.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public interface SimpleServer {

//    private void start() throws IOException, SQLException;

    void requestHandler();

    void responseHandler();

}
