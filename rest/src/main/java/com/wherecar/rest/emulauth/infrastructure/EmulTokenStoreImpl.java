package com.wherecar.rest.emulauth.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class EmulTokenStoreImpl implements EmulTokenStore{

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void saveToken(String mdn, String token, long expireSeconds) {
        String key = "token:" + mdn;
        redisTemplate.opsForValue().set(key, token, Duration.ofSeconds(expireSeconds));
    }

    @Override
    public String getToken(String mdn) {
        String key = "token:" + mdn;
        Object value = redisTemplate.opsForHash().get(key, "token");
        return value != null ? value.toString() : null;
    }

}
