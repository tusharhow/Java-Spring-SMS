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
    private DataService dataService;

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage(Model model) { 
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) { 
      
        User user = dataService.authenticateUser(username, password);
        
        if (user != null) {
            session.setAttribute("user", user);
            
            if ("ADMIN".equals(user.getRole())) {
                return "redirect:/dashboard";
            } else if ("STUDENT".equals(user.getRole())) {
                return "redirect:/student-dashboard";
            }
        }

        model.addText("Invalid credentials");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}