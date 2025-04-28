package com.wherecar.rest.emulauth.application;

import com.wherecar.rest.emulauth.application.dto.EmulTokenRequest;
import com.wherecar.rest.emulauth.application.dto.EmulTokenResponse;
import com.wherecar.rest.emulauth.jwt.EmulJWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EmulAuthServiceImpl implements EmulAuthService{

    private final EmulJWTUtil emulJWTUtil;
    private static final long EXPIRE_DAYS = 4 * 24 * 60 * 60 * 1000L;

    @Override
    public EmulTokenResponse issueToken(EmulTokenRequest requestDto) {
        String mdn = requestDto.getMdn();
        String token = emulJWTUtil.createEmulToken(mdn, EXPIRE_DAYS);

        EmulTokenResponse emulTokenResponse = EmulTokenResponse.builder()
                .rstCd("000")
                .rstMsg("Success")
                .mdn(mdn)
                .token(token)
                .exPeriod(String.valueOf(EXPIRE_DAYS))
                .build();

        return emulTokenResponse;

    }

}
