package com.wherecar.rest.emulauth.hubtmp.token.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmulTokenReaderImpl implements EmulTokenReader {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public String getTokenBymdn(String mdn) {
        return redisTemplate.opsForValue().get("token:" + mdn);
    }
}
