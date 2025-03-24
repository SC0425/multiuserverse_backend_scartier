package edu.uscb.csci470sp25.multiuserverse_backend.config;

import edu.uscb.csci470sp25.multiuserverse_backend.security.JwtAuthenticationFilter;
import edu.uscb.csci470sp25.multiuserverse_backend.security.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtUtil jwtUtil;

    public SecurityConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            // ✅ Public Endpoints (Allow anyone to register or log in)
            .requestMatchers("/auth/register", "/auth/login").permitAll()
            
            // ✅ Guest users can view all users and specific user profiles
            .requestMatchers(HttpMethod.GET, "/users").permitAll()
            .requestMatchers(HttpMethod.GET, "/user/{id}").permitAll()

            // ✅ Role-Based Restrictions
            .requestMatchers(HttpMethod.PUT, "/user/{id}").hasAnyAuthority("ADMIN", "PRIVILEGED_USER") // Edit users
            .requestMatchers(HttpMethod.DELETE, "/user/{id}").hasAuthority("ADMIN") // Delete users
            .requestMatchers(HttpMethod.POST, "/user").hasAuthority("ADMIN") // Add users

            .anyRequest().authenticated() // Secure all other endpoints
        )
        
        .addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
        .httpBasic(httpBasic -> httpBasic.disable()) // Disable HTTP Basic Auth
        .formLogin(login -> login.disable()); // Disable Form Login

        System.out.println("✅ SecurityConfig initialized with Role-Based Access Control (RBAC)");

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}