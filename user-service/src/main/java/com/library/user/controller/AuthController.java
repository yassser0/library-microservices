package com.library.user.controller;

import com.library.user.entity.User;
import com.library.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

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
    public ResponseEntity<String> login(@RequestBody User user,HttpSession session) {
        Optional<User> existing = userService.login(user.getEmail(), user.getPassword());
        if (existing.isPresent()) {
            session.setAttribute("user", existing.get()); 
            return ResponseEntity.ok("Connexion réussie");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Identifiants invalides");
        }
    }
    @PostMapping("/logout")
public ResponseEntity<String> logout(HttpServletRequest request) {
    request.getSession().invalidate();
    return ResponseEntity.ok("Déconnexion réussie");
}
@GetMapping("/check-session")
public ResponseEntity<?> checkSession(HttpSession session) {
    Object user = session.getAttribute("user");
    if (user != null) {
        return ResponseEntity.ok("connecté");
    } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("non connecté");
    }
}
}
