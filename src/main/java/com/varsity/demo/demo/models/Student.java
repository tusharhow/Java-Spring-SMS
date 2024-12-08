package com.varsity.demo.demo.models;



public class Student {
    private Long id;
    private String name;
    private String rollNumber;
    private String className;

    // Default constructor
    public Student() {}

    // Constructor with parameters
    public Student(Long id, String name, String rollNumber, String className) {
        this.id = id;
        this.name = name;
        this.rollNumber = rollNumber;
        this.className = className;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRollNumber() { return rollNumber; }
    public void setRollNumber(String rollNumber) { this.rollNumber = rollNumber; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
}