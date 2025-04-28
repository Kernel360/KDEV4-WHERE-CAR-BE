package com.wherecar.hub.token.application;

import com.wherecar.hub.common.jwt.EmulJWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenValidationService {

    private final EmulJWTUtil emulJWTUtil;

    public TokenValidationResult validate(String token) {

        if (token == null || token.isBlank()) {
            return TokenValidationResult.MISSING;
        }

        try {
            if (emulJWTUtil.isExpired(token)) {
                return TokenValidationResult.INVALID;
            }
            return TokenValidationResult.SUCCESS;
        } catch (Exception e) {
            return TokenValidationResult.INVALID;
        }
    }
}