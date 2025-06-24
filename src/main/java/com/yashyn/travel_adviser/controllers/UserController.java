package com.yashyn.travel_adviser.controllers;

import com.yashyn.travel_adviser.data.dto.UserDto;
import com.yashyn.travel_adviser.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> findAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/{login}")
    public ResponseEntity<UserDto> findUserByLogin(@PathVariable String login) {
        return ResponseEntity.ok(userService.findUserByLogin(login));
    }

    @GetMapping("/me")
    public  ResponseEntity<UserDto> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        String login = userDetails.getUsername();
        return ResponseEntity.ok(userService.findUserByLogin(login));
    }
}
