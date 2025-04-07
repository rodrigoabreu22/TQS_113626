package org.tqs.deti.ua.MoliceiroUniRestaurants.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Allow ALL requests without authentication
                )
                .csrf(csrf -> csrf.disable()); // Disable CSRF (optional, but recommended for APIs)

        return http.build();
    }
}
