package com.varsity.demo.demo.controllers;

import com.varsity.demo.demo.data.*;
import com.varsity.demo.demo.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpSession;


@Controller
public class LoginController {

    @Autowired
    public DataService dataService;

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {  // Add Model parameter
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {  // Use org.springframework.ui.Model
        // Hardcoded credentials for testing
        if (username.equals("admin") && password.equals("admin123")) {
            User user = new User(username, password, "ADMIN");
            session.setAttribute("user", user);
            return "redirect:/dashboard";
        }

        model.addText( "Invalid credentials");  // Use addAttribute instead of addText
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}