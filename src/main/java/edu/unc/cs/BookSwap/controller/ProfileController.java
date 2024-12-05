package edu.unc.cs.BookSwap.controller;

import edu.unc.cs.BookSwap.dto.UserDto;
import edu.unc.cs.BookSwap.entity.User;
import edu.unc.cs.BookSwap.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    @Autowired
    AppUserService appUserService;

    @GetMapping("/user/profile/edit")
    public String showUserProfile(Authentication authentication, Model model) {
        String email = authentication.getName();
        User userByEmail = appUserService.findUserByEmail(email);
        model.addAttribute("user", userByEmail);
        return "user_profile";
    }

}
