package main;

public class User {
	private int userid;
	private String username;
	private String usersurname;
		
	public User(int userid, String username, String usersurname) {
		super();
		this.userid = userid;
		this.username = username;
		this.usersurname = usersurname;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsersurname() {
		return usersurname;
	}
	public void setUsersurname(String usersurname) {
		this.usersurname = usersurname;
	}
	
	
}
