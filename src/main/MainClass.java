package main;

import java.sql.Statement;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainClass {

	public static void main(String[] args) throws IOException, URISyntaxException, SQLException {
		// TODO Auto-generated method stub

		int port = 1234;
		ServerSocket server = new ServerSocket(port);

		System.out.println("Listening for connection on port " + port + "...");

		while (true) {
			try (Socket socket = server.accept()) {
				// Initialize reader and writer
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter out = new PrintWriter(socket.getOutputStream());
				// Start sending reply, using the HTTP 1.1 protocol
				out.print("HTTP/1.1 200 \r\n"); 
				out.print("Content-Type: text/plain\r\n"); 
				out.print("Connection: close\r\n"); 
				out.print("\r\n"); 
				
				// Read HTTP request from the client
				String line = null;
				String method = null;
				String symbols = null;
				String parts[] = null;
				while ((line = in.readLine()) != null && line.length() != 0) {
					if (parts == null) 
						parts = line.split("/");
								
					out.print(line + "\r\n");
				}
				
				int id = 0;
				String httpResponse = null;
				
				try {
					method = parts[0];
					method = method.substring(0, method.length() - 1); // Remove space
					symbols = parts[1];
					System.out.println(method + " " + symbols);
					//parts = symbols.split(" ");
					
					// Check if ID is integer
					if(tryParseInt(parts[1])) 
						id = new Integer(parts[1]);
					
					// Return HTTP response with info about user, stored in DB
					if("GET".equals(method)) {
						httpResponse = getUserInfo(id);
					}
					else if("POST".equals(method)) {
						String name = parts[2];
						String surname = parts[3];
						httpResponse = addNewUser(new User(id, name, surname));
					}
					//System.out.println(id);
				} catch (NumberFormatException e) {
					System.out.println("Invalid id number");
				}
				
				out.print(httpResponse); // Print info about user
				out.close(); // Flush and close the output stream
				in.close(); // Close the input stream
			}
		}
	}

	private static String addNewUser(User user) throws SQLException {
		// TODO Auto-generated method stub
		try {
			Class.forName("org.h2.Driver");
			Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "");

			Statement st = conn.createStatement();
			String query = "SELECT * FROM user WHERE ID = " + user.getUserid() + ";";

			ResultSet rs = st.executeQuery(query);
			if(!rs.next()) {
				String sql = "INSERT INTO user VALUES ('" + user.getUserid() + "', '" + user.getUsername() + "', '" + user.getUsersurname() + "');";
				st.executeUpdate(sql);
				System.out.println("User successfully inserted!");
			}
			else {
				return "User with id " + user.getUserid() + " already exists\n" + getUserInfo(user.getUserid());
			}
			
			conn.close();
			return "User successfully added\n" + getUserInfo(user.getUserid());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Error while adding new user\n";
		}
	}

	private static String getUserInfo(int id) {
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

	public static User getUserById(int id) throws SQLException {
		String name = null;
		String surname = null;
		try {
			Class.forName("org.h2.Driver");
			Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "");

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

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new User(id, name, surname);
	}
	
	public static boolean tryParseInt(String value) {  
	     try {  
	         Integer.parseInt(value);  
	         return true;  
	      } catch (NumberFormatException e) {  
	         return false;  
	      }  
	}
}
