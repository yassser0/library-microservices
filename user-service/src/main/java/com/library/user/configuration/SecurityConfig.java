package com.library.user.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Désactive CSRF pour les appels API
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/auth/**").permitAll() // Autorise l'accès aux routes /auth/*
                        .anyRequest().authenticated())
                .httpBasic(basic -> basic.disable()) // Désactive l'authentification basique
                .formLogin(login -> login.disable()); // Désactive le formulaire de login Spring par défaut

        return http.build();
    }
}
