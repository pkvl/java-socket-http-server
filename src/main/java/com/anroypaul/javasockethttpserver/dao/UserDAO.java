package com.anroypaul.javasockethttpserver.dao;

import com.anroypaul.javasockethttpserver.ConnectionManager;
import com.anroypaul.javasockethttpserver.domain.User;
import com.anroypaul.javasockethttpserver.helpers.SqlQueries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO {

    private final Logger LOGGER = LogManager.getLogger(UserDAO.class);
    private Connection conn = null;
    private Statement st = null;
    private ResultSet rs = null;

    public UserDAO() throws SQLException {
        // Connect to database
        conn = ConnectionManager.getConnection();
        try {
            st = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String add(User user) throws SQLException {
        try {
            if (!isExist(user)) {
                LOGGER.info("User with ID " + user.getId() + " not exists. Adding to database");
                String[] values = new String[3];
                values[0] = Integer.toString(user.getId());
                values[1] = user.getName();
                values[2] = user.getSurname();
                String sql = SqlQueries.insertIntoTableValues(User.TABLE_NAME, values);
                st.executeUpdate(sql);
//                System.out.println("User successfully inserted!");
                LOGGER.info("User successfully inserted!");
            } else {
                LOGGER.error("User with id " + user.getId() + " already exists");
                return "User with id " + user.getId() + " already exists\n" + getInfoById(user.getId());
            }

            return "User successfully added\n" + getInfoById(user.getId());
        } catch (SQLException e) {
            LOGGER.error("Error while adding a new user");
            e.printStackTrace();
            return "Error while adding a new user\n";
        } finally {
            LOGGER.info("Closing a database connection");
            conn.close();
        }
    }

    private boolean isExist(User user) throws SQLException {
        LOGGER.info("Checking if user with ID " + user.getId() + "exists");
        String query = SqlQueries.getEntityById(User.TABLE_NAME, user.getId());
        rs = st.executeQuery(query);
        return rs.next();
    }

    public User getById(int id) throws SQLException {
        String name = null;
        String surname = null;
        try {
            LOGGER.info("Getting info about user with id " + id);
            Statement st = conn.createStatement();
            String query = SqlQueries.getEntityById(User.TABLE_NAME, id);
            rs = st.executeQuery(query);

            while (rs.next()) {
                name = rs.getString("NAME");
                surname = rs.getString("SURNAME");
                // System.out.println(String.format("%10s %5s %5s", id, name, surname));
            }
        } catch (SQLException e) {
            LOGGER.error("Error while getting info about user");
            e.printStackTrace();
        } finally {
            LOGGER.info("Closing a database connection");
            conn.close();
        }
        return new User(id, name, surname);
    }

    public String getInfoById(int id) {
        String httpResponse = null;
        try {
            User u = getById(id);
            httpResponse = "\nID   NAME   SURNAME\n"
                    + u.getId() + "   " + u.getName() + "   " + u.getSurname() + "\n";
        } catch (SQLException e) {
            LOGGER.error("Error while getting info about user");
            e.printStackTrace();
        }
        return httpResponse;
    }

}
