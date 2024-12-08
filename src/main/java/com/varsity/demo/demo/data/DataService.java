package com.varsity.demo.demo.data;


import com.varsity.demo.demo.models.User;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class DataService {
    private List<User> users;

    public DataService() {
        users = new ArrayList<>();
        // Add default admin user
        users.add(new User("admin", "admin123", "ADMIN"));
    }

    public User findByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
}