package com.yashyn.travel_adviser.controllers;

import com.yashyn.travel_adviser.data.entities.User;
import com.yashyn.travel_adviser.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public String findAllUsers(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "users";
    }

    @GetMapping("/{login}")
    public String findUserByLogin(@PathVariable String login, Model model) {
        model.addAttribute("user", userService.findUserByLogin(login));
        return "user-details";
    }

    @GetMapping("/me")
    public String getCurrentUser(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String login = userDetails.getUsername();
        User user = userService.findUserByLogin(login);
        model.addAttribute("user", user);
        return "user-details";
    }
}
