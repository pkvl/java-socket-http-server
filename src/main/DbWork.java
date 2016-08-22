package main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;


public class DbWork {

    private final Logger logger = Logger.getLogger(this.getClass());
    private Connection conn = null;
    private Statement st = null;
    private ResultSet rs = null;

    public DbWork() {
        // Connect to database
        conn = ConnectionManager.getConnection();
        try {
            st = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String addNewUser(User user) throws SQLException {
        try {
            logger.trace("Checking if user with " + user.getUserid() + "exists");
            String query = "SELECT * FROM user WHERE ID = " + user.getUserid() + ";";

            rs = st.executeQuery(query);
            if (!rs.next()) {
                logger.trace("User with if " + user.getUserid() + " not exists. Adding to databse");
                String sql = "INSERT INTO user VALUES ('" + user.getUserid() + "', '" + user.getUsername() + "', '" + user.getUsersurname() + "');";
                st.executeUpdate(sql);
//                System.out.println("User successfully inserted!");
                logger.trace("User successfully inserted!");
            } else {
                logger.error("User with id " + user.getUserid() + " already exists");
                return "User with id " + user.getUserid() + " already exists\n" + getUserInfo(user.getUserid());
            }

            return "User successfully added\n" + getUserInfo(user.getUserid());
        } catch (SQLException e) {
            logger.error("Error while adding new user");
            e.printStackTrace();
            return "Error while adding new user\n";
        } finally {
            logger.trace("Closing database connection");
            conn.close();
        }
    }

    public User getUserById(int id) throws SQLException {
        String name = null;
        String surname = null;
        try {
            logger.trace("Getting info about user with id " + id);
            Statement st = conn.createStatement();
            String query = "SELECT * FROM user WHERE ID = " + id + ";";
            rs = st.executeQuery(query);

            //System.out.println(String.format("%10s %5s %5s", "ID", "NAME", "SURNAME"));

            while (rs.next()) {
                name = rs.getString("NAME");
                surname = rs.getString("SURNAME");
                //System.out.println(String.format("%10s %5s %5s", id, name, surname));
            }

        } catch (SQLException e) {
            logger.error("Error while getting info about user");
            e.printStackTrace();
        } finally {
            logger.trace("Closing database connection");
            conn.close();
        }
        return new User(id, name, surname);
    }

    public String getUserInfo(int id) {
        String httpResponse = null;
        try {
            User u = getUserById(id);
            httpResponse = "\nID   NAME   SURNAME\n"
                    + u.getUserid() + "   " + u.getUsername() + "   " + u.getUsersurname() + "\n";
        } catch (SQLException e) {
            logger.error("Error while getting info about user");
            e.printStackTrace();
        }
        return httpResponse;
    }

}
