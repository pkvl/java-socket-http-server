package com.anroypaul.javasockethttpserver.domain;

public class User {

    public static String TABLE_NAME = "USER";

    private int id;
    private String firstName;
    private String lastName;

    public User() {}

    public User(int id, String name, String surname) {
        this.id = id;
        this.firstName = name;
        this.lastName = surname;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
