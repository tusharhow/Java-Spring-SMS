package com.varsity.demo.demo.models;

public class User {
    private static int counter = 1;
    private int id;
    private String username;

    public static int getCounter() {
        return counter;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static void setCounter(int counter) {
        User.counter = counter;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    private String password;
    private String role;

    public User(String username, String password, String role) {
        this.id = counter++;
        this.username = username;
        this.password = password;
        this.role = role;
    }

}