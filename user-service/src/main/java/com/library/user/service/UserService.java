package com.library.user.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.user.entity.User;
import com.library.user.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public User register(User user) {
        return repository.save(user);
    }

    public Optional<User> login(String email, String password) {
        return repository.findByEmail(email)
            .filter(u -> u.getPassword().equals(password));
    }
}