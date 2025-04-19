package com.example.Sahtech.security;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Service to manage blacklisted JWT tokens after logout
 */
@Service
public class TokenBlacklistService {

    // Using ConcurrentHashMap for thread safety
    private final Map<String, Long> blacklistedTokens = new ConcurrentHashMap<>();
    private final ScheduledExecutorService cleanupExecutor;

    public TokenBlacklistService() {
        // Schedule periodic cleanup of expired tokens
        cleanupExecutor = Executors.newSingleThreadScheduledExecutor();
        cleanupExecutor.scheduleAtFixedRate(
                this::cleanupExpiredTokens,
                1, 1, TimeUnit.HOURS
        );
    }

    /**
     * Add a token to the blacklist with its expiration time
     * 
     * @param token the JWT token to blacklist
     * @param expirationTimeMillis the token's expiration time in milliseconds
     */
    public void blacklistToken(String token, long expirationTimeMillis) {
        blacklistedTokens.put(token, expirationTimeMillis);
    }

    /**
     * Check if a token is blacklisted
     * 
     * @param token the JWT token to check
     * @return true if the token is blacklisted, false otherwise
     */
    public boolean isBlacklisted(String token) {
        return blacklistedTokens.containsKey(token);
    }

    /**
     * Remove expired tokens from the blacklist
     */
    private void cleanupExpiredTokens() {
        long currentTimeMillis = System.currentTimeMillis();
        
        // Create a set of tokens to remove to avoid ConcurrentModificationException
        Set<String> tokensToRemove = blacklistedTokens.entrySet().stream()
                .filter(entry -> entry.getValue() < currentTimeMillis)
                .map(Map.Entry::getKey)
                .collect(java.util.stream.Collectors.toSet());
        
        // Remove expired tokens
        tokensToRemove.forEach(blacklistedTokens::remove);
    }
} 