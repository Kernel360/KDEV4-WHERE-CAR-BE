package com.wherecar.rest.emulauth.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class EmulTokenStoreImpl implements EmulTokenStore{

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void saveToken(String mdn, String token, long expireDays) {
        String key = "token:" + mdn;
        redisTemplate.opsForValue().set(key, token, expireDays, TimeUnit.DAYS);
    }
}
