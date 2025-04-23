package com.wherecar.rest.emulauth.hubtmp.token.application;

import com.wherecar.rest.emulauth.hubtmp.token.infrastructure.EmulTokenReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenValidationService {

    private final EmulTokenReader emulTokenReader;

    public TokenValidationResult validate(String mdn, String token) {
        String storedToken = emulTokenReader.getTokenBymdn(mdn);

        if (storedToken == null) {
            return TokenValidationResult.MISSING;
        }

        if (!storedToken.equals(token)) {
            return TokenValidationResult.INVALID;
        }

        return TokenValidationResult.SUCCESS;
    }
}