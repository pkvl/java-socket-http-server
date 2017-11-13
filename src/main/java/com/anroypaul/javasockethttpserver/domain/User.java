package com.anroypaul.javasockethttpserver.domain;

public class User {
    private int id;
    private String name;
    private String surname;

    public User(int id, String name, String surname) {
        super();
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public int getId() {
        return id;
    }

    //	public void setUserid(int id) {
//		this.id = id;
//	}
    public String getName() {
        return name;
    }

    //	public void setUsername(String name) {
//		this.name = name;
//	}
    public String getSurname() {
        return surname;
    }
//	public void setUsersurname(String surname) {
//		this.surname = surname;
//	}
	
	
}
