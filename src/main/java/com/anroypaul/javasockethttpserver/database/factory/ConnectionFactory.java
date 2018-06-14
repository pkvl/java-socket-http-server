package com.anroypaul.javasockethttpserver.database.factory;

import com.anroypaul.javasockethttpserver.database.Connection;

public interface ConnectionFactory {

    Connection getConnection();
}