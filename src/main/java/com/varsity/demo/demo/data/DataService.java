package com.varsity.demo.demo.data;

import com.varsity.demo.demo.models.User;
import com.varsity.demo.demo.models.Student;
import com.varsity.demo.demo.services.LocalStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataService {
    private List<User> users;
    private final LocalStorageService storageService;

    @Autowired
    public DataService(LocalStorageService storageService) {
        this.storageService = storageService;
        this.users = new ArrayList<>();
        initializeUsers();
        initializeStudents();
    }

    private void initializeUsers() {
        // Admin user
        users.add(new User("admin", "admin123", "ADMIN"));
        
        // Static student credentials
        users.add(new User("student1", "student123", "STUDENT", "STU001"));
        users.add(new User("student2", "student123", "STUDENT", "STU002"));
        users.add(new User("student3", "student123", "STUDENT", "STU003"));
    }

    private void initializeStudents() {
        List<Student> existingStudents = storageService.loadStudents();
        if (existingStudents.isEmpty()) {
            List<Student> students = new ArrayList<>();
            students.add(new Student(1L, "John Doe", "STU001", "Class A"));
            students.add(new Student(2L, "Jane Smith", "STU002", "Class B"));
            students.add(new Student(3L, "Bob Wilson", "STU003", "Class A"));
            storageService.saveStudents(students);
        }
    }

    public User findByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public User authenticateUser(String username, String password) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username) 
                    && user.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
}