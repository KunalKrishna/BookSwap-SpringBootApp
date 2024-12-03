package edu.unc.cs.BookSwap.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

//@AllArgsConstructor
//@Controller
public class ViewController {

    @GetMapping({" ", "/login", "/login"})
    public String showLoginPage() {
        return "login";  // Refers to login.html
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";  // Refers to register.html
    }

//    @PostMapping("/register")
//    public String registerUser(UserDetailsDto userDetailsDto) {
//        // Handle user registration logic here (save to database)
//        return "redirect:/login?registered";  // Redirect to login page after successful registration
//    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "user_dashboard";  // Refers to user_dashboard.html in templates folder
    }

    @GetMapping("/home")
    public String home() {
        return "home";  // Refers to home.html
    }
}
