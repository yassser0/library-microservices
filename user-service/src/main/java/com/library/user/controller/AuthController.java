package com.library.user.controller;

import com.library.user.entity.User;
import com.library.user.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private UserService userService;

   @PostMapping("/register")
public ResponseEntity<?> register(@RequestBody User user) {
    try {
        User registered = userService.register(user);
        return ResponseEntity.ok(registered);
    } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        Optional<User> existing = userService.login(user.getEmail(), user.getPassword());
        return existing.isPresent() ? "Connexion réussie" : "Identifiants invalides";
    }
}
