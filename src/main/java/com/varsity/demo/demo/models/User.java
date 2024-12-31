package com.varsity.demo.demo.models;

public class User {
    private static int counter = 1;
    private int id;
    private String username;
    private String password;
    private String role;
    private String studentId;

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

    public User(String username, String password, String role) {
        this.id = counter++;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User(String username, String password, String role, String studentId) {
        this(username, password, role);
        this.studentId = studentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

}