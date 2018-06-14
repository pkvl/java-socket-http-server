package com.anroypaul.javasockethttpserver;

import com.anroypaul.javasockethttpserver.database.factory.ConnectionFactory;
import com.anroypaul.javasockethttpserver.database.factory.H2DatabaseConnectionFactory;

public class ConnectionManager {

    public static ConnectionFactory getConnectionFactory(DatabaseType databaseType) {
        switch (databaseType) {
            default:
                return new H2DatabaseConnectionFactory();
        }
    }

    public enum DatabaseType {
       H2;
    }

}
