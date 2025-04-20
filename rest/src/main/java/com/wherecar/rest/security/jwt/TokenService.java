package com.wherecar.rest.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final RedisTemplate<String, String> redisTemplate;

    private static final long REFRESH_TTL_MS = 3 * 24 * 60 * 60 * 1000L;

    public void saveRefreshToken(String email, String refreshToken) {
        redisTemplate.opsForValue().set("refresh:user:" + email, refreshToken, REFRESH_TTL_MS, TimeUnit.MILLISECONDS);
    }

    public boolean isRefreshTokenValid(String email, String providedToken) {
        String saved = redisTemplate.opsForValue().get("refresh:user:" + email);
        return providedToken.equals(saved);
    }

    public void removeRefreshToken(String email) {
        redisTemplate.delete("refresh:user:" + email);
    }
}

