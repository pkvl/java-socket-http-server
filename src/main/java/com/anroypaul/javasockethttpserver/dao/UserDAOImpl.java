package com.anroypaul.javasockethttpserver.dao;

import com.anroypaul.javasockethttpserver.database.Connection;
import com.anroypaul.javasockethttpserver.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private Connection connection;
    private Statement statement;

    private final Logger LOGGER = LogManager.getLogger(UserDAOImpl.class);

    private UserDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public List<User> findAll() {
        String sql = "SELECT * FROM " + User.TABLE_NAME + ";";
        List<User> users = new ArrayList<>();

        try {
            ResultSet resultSet = statement.executeQuery(sql);
            LOGGER.info("Executing SQL : " + sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                users.add(user);
            }

            LOGGER.info("SQL executed successfully");
        } catch (SQLException e) {
            LOGGER.error("Failed to execute SQL");
        }

        return users;
    }

    public int count() {
        String sql = "SELECT COUNT(*) FROM " + User.TABLE_NAME + ";";
        int result = 0;
        try {
            ResultSet resultSet = statement.executeQuery(sql);
            LOGGER.info("Executing SQL : " + sql);
            while (resultSet.next()) {
                result = resultSet.getInt(0);
            }

            LOGGER.info("SQL executed successfully");
        } catch (SQLException e) {
            LOGGER.error("Failed to execute SQL");
        }

        return result;
    }

    public void save(User user) {
        String sql = "INSERT INTO " + User.TABLE_NAME + " (first_name, email, birth_date) VALUES (?, ?, ?)";
    }

    public void update(User user) {
        String sql = "UPDATE " + User.TABLE_NAME + " SET first_name = ?, email = ?, birth_date = ? WHERE id = ?";

    }

    public void delete(int id) {
        String sql = "SELECT * FROM " + User.TABLE_NAME  + " WHERE id = ?";
    }

    public User getById(int id) {
        String sql = "SELECT FROM " + User.TABLE_NAME + " WHERE id = ?";
        User user = new User();
        try {
            ResultSet resultSet = statement.executeQuery(sql);
            LOGGER.info("Executing SQL : " + sql);
            while (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
            }

            LOGGER.info("SQL executed successfully");
        } catch (SQLException e) {
            LOGGER.error("Failed to execute SQL");
        }
        return null;
    }

    public boolean isExist(int id) {
        return false;
    }
}
