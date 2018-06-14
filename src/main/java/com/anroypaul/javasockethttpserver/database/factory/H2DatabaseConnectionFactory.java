package com.anroypaul.javasockethttpserver.database.factory;

import com.anroypaul.javasockethttpserver.database.Connection;
import com.anroypaul.javasockethttpserver.database.H2DatabaseConnection;

public class H2DatabaseConnectionFactory implements ConnectionFactory {

    @Override
    public Connection getConnection() {
        return new H2DatabaseConnection();
    }
}
