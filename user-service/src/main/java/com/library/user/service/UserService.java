package com.library.user.service;

import com.library.user.entity.User;
import com.library.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User register(User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Cet email est déjà utilisé.");
        }

        // Hash du mot de passe
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> login(String email, String password) {
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            // Comparaison entre le mot de passe brut et le mot de passe hashé
            boolean passwordMatch = passwordEncoder.matches(password, existingUser.get().getPassword());
            return passwordMatch ? existingUser : Optional.empty();
        }
        return Optional.empty();
    }
}
