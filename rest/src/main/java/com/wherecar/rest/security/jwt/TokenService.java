package com.wherecar.rest.security.jwt;

import com.wherecar.rest.common.exception.TokenValidationException;
import com.wherecar.rest.security.jwt.dto.TokenPair;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class TokenService {

    private final JWTUtil jwtUtil;
    private final RedisTemplate<String, String> redisTemplate;

    public static final long FRESH_TTL_MS = 60 * 60 * 1000L;
    public static final long REFRESH_TTL_MS = 3 * 24 * 60 * 60 * 1000L;

    public TokenPair issueTokens(String email) {
        String access = jwtUtil.createJwt("access", email, FRESH_TTL_MS);
        String refresh = jwtUtil.createJwt("refresh", email, REFRESH_TTL_MS);

        //Redis 덮어씌우기
        redisTemplate.opsForValue().set("refresh:user:" + email, refresh, REFRESH_TTL_MS, TimeUnit.MILLISECONDS);

        return new TokenPair(access, refresh);
    }

    public void validateRefreshToken(String token) {
        //Todo: 에러 메세지 별도 파일에 정리하기

        // 1. 만료 체크
        if (jwtUtil.isExpired(token)) {
            throw new TokenValidationException("Refresh token expired");
        }

        // 2. category 체크
        if (!"refresh".equals(jwtUtil.getCategory(token))) {
            throw new TokenValidationException("Invalid refresh token type");
        }

        // 3. Redis 화이트리스트 확인
        String email = jwtUtil.getEmail(token);
        if (!token.equals(redisTemplate.opsForValue().get("refresh:user:" + email))) {
            throw new TokenValidationException("Refresh token not in whitelist");
        }
    }

    public void removeRefreshToken(String refreshToken) {
        String email = jwtUtil.getEmail(refreshToken);
        redisTemplate.delete("refresh:user:" + email);
    }
}

