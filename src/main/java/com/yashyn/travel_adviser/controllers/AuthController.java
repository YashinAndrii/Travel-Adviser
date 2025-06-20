package com.yashyn.travel_adviser.controllers;

import com.yashyn.travel_adviser.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register-form")
    public String registerViaForm(@RequestParam String login, @RequestParam String password, Model model) {
        try {
            userService.register(login, password);
            model.addAttribute("message", "Registered successfully");
            return "redirect:/auth/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login-form")
    public String loginViaForm(@RequestParam String login, @RequestParam String password, Model model) {
        try {
            userService.login(login, password);
            model.addAttribute("message", "Logged in");
            return "redirect:/trips/my";
        } catch (Exception e) {
            model.addAttribute("error", "Invalid login or password");
            return "login";
        }
    }
}
