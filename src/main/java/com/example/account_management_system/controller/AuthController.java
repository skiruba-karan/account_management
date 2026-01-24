package com.example.account_management_system.controller;

import com.example.account_management_system.config.JwtUtil;
import com.example.account_management_system.model.User;
import com.example.account_management_system.repository.UserRepository;
import com.example.account_management_system.service.LogoutService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;
    private final LogoutService logoutService;


    public AuthController(UserRepository userRepository, PasswordEncoder encoder, JwtUtil jwtUtil, LogoutService logoutService) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
        this.logoutService = logoutService;
    }

    @PostMapping("/register")
    public String register(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        user.setRole("ROLE_USER");

        userRepository.save(user);

        return "User registered successfully";
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!encoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

        return Map.of("token", token);
    }

    @RestController
    @RequestMapping("/auth")
    public class LogoutController {

        private final LogoutService logoutService;

        public LogoutController(LogoutService logoutService) {
            this.logoutService = logoutService;
        }

        @PostMapping("/logout")
        public ResponseEntity<?> logout(
                @RequestHeader("Authorization") String authHeader) {

            logoutService.logout(authHeader);
            return ResponseEntity.ok(
                    Map.of("message", "Logout successful")
            );
        }
    }

}
