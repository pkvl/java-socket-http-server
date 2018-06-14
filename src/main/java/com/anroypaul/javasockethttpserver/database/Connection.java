package com.anroypaul.javasockethttpserver.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Connection {

    public void connect();

    public void executeQuery(String sql);

    public void mapResult(ResultSet resultSet, Object model);

    public void close() throws SQLException;
}