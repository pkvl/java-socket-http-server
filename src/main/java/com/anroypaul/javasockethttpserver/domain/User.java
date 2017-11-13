package com.anroypaul.javasockethttpserver.domain;

public class User {

    public static String TABLE_NAME = "USER";

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

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

}
