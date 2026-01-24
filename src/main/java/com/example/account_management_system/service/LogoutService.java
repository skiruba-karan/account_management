package com.example.account_management_system.service;

import com.example.account_management_system.config.JwtUtil;
import com.example.account_management_system.model.BlacklistedToken;
import com.example.account_management_system.repository.BlacklistedTokenRepository;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;

@Service
public class LogoutService {

    private final BlacklistedTokenRepository repository;
    private final JwtUtil jwtUtil;

    public LogoutService(BlacklistedTokenRepository repository, JwtUtil jwtUtil) {
        this.repository = repository;
        this.jwtUtil = jwtUtil;
    }

    public void logout(String token) {
        token = token.replace("Bearer ", "");

        Date expiry = jwtUtil.extractExpiration(token);

        BlacklistedToken blacklisted = new BlacklistedToken();
        blacklisted.setToken(token);
        blacklisted.setExpiry(
                expiry.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime()
        );

        repository.save(blacklisted);
    }

    public boolean isBlacklisted(String token) {
        return repository.existsByToken(token);
    }
}
