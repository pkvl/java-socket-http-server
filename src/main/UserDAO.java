package main;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO {

    private final Logger LOG = Logger.getLogger(this.getClass());
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
                LOG.trace("User with if " + user.getId() + " not exists. Adding to databse");
                String sql = "INSERT INTO user VALUES ('" + user.getId() + "', '" + user.getName() + "', '" + user.getSurname() + "');";
                st.executeUpdate(sql);
//                System.out.println("User successfully inserted!");
                LOG.trace("User successfully inserted!");
            } else {
                LOG.error("User with id " + user.getId() + " already exists");
                return "User with id " + user.getId() + " already exists\n" + getInfoById(user.getId());
            }

            return "User successfully added\n" + getInfoById(user.getId());
        } catch (SQLException e) {
            LOG.error("Error while adding new user");
            e.printStackTrace();
            return "Error while adding new user\n";
        } finally {
            LOG.trace("Closing database connection");
            conn.close();
        }
    }

    private boolean isExist(User user) throws SQLException {
        LOG.trace("Checking if user with " + user.getId() + "exists");
        String query = "SELECT * FROM user WHERE ID = " + user.getId() + ";";
        rs = st.executeQuery(query);
        return rs.next();
    }

    public User getById(int id) throws SQLException {
        String name = null;
        String surname = null;
        try {
            LOG.trace("Getting info about user with id " + id);
            Statement st = conn.createStatement();
            String query = "SELECT * FROM user WHERE ID = " + id + ";";
            rs = st.executeQuery(query);

            while (rs.next()) {
                name = rs.getString("NAME");
                surname = rs.getString("SURNAME");
                //System.out.println(String.format("%10s %5s %5s", id, name, surname));
            }
        } catch (SQLException e) {
            LOG.error("Error while getting info about user");
            e.printStackTrace();
        } finally {
            LOG.trace("Closing database connection");
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
            LOG.error("Error while getting info about user");
            e.printStackTrace();
        }
        return httpResponse;
    }

}
