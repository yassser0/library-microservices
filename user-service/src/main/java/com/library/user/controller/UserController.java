package com.library.user.controller;

import com.library.user.entity.User;
import com.library.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserRepository repository;

    // Récupérer tous les utilisateurs
    @GetMapping
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    // Créer un nouvel utilisateur
    @PostMapping
    public User createUser(@RequestBody User user) {
        return repository.save(user);
    }

    // 🔁 Mettre à jour les infos personnelles (nom, email)
    @PutMapping("/update-info")
    public ResponseEntity<?> updateUserInfo(@RequestBody User updatedUser) {
        Optional<User> optionalUser = repository.findByEmail(updatedUser.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setName(updatedUser.getName());
            return ResponseEntity.ok(repository.save(user));
        } else {
            return ResponseEntity.badRequest().body("Utilisateur non trouvé.");
        }
    }

    // 🔐 Mettre à jour le mot de passe
    @PutMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String currentPassword = body.get("currentPassword");
        String newPassword = body.get("newPassword");

        Optional<User> optionalUser = repository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (!user.getPassword().equals(currentPassword)) {
                return ResponseEntity.badRequest().body("Mot de passe actuel incorrect.");
            }
            user.setPassword(newPassword);
            repository.save(user);
            return ResponseEntity.ok("Mot de passe mis à jour.");
        } else {
            return ResponseEntity.badRequest().body("Utilisateur non trouvé.");
        }
    }
}
