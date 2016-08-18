package main;

import java.sql.*;

public class DbWork {

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
        // TODO Auto-generated method stub
        try {
            String query = "SELECT * FROM user WHERE ID = " + user.getUserid() + ";";

            ResultSet rs = st.executeQuery(query);
            if (!rs.next()) {
                String sql = "INSERT INTO user VALUES ('" + user.getUserid() + "', '" + user.getUsername() + "', '" + user.getUsersurname() + "');";
                st.executeUpdate(sql);
                System.out.println("User successfully inserted!");
            } else {
                return "User with id " + user.getUserid() + " already exists\n" + getUserInfo(user.getUserid());
            }

            return "User successfully added\n" + getUserInfo(user.getUserid());
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "Error while adding new user\n";
        }
    }

    public User getUserById(int id) throws SQLException {
        String name = null;
        String surname = null;
        try {
            Statement st = conn.createStatement();
            String query = "SELECT * FROM user WHERE ID = " + id + ";";
            ResultSet rs = st.executeQuery(query);

            //System.out.println(String.format("%10s %5s %5s", "ID", "NAME", "SURNAME"));

            while (rs.next()) {
                name = rs.getString("NAME");
                surname = rs.getString("SURNAME");
                //System.out.println(String.format("%10s %5s %5s", id, name, surname));
            }

            conn.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return httpResponse;
    }

}
