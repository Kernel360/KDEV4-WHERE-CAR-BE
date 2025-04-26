package com.wherecar.rest.emulauth.application;

import com.wherecar.rest.emulauth.application.dto.EmulTokenRequest;
import com.wherecar.rest.emulauth.application.dto.EmulTokenResponse;
import com.wherecar.rest.emulauth.infrastructure.EmulTokenStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EmulAuthServiceImpl implements EmulAuthService{

    private final EmulTokenStore emulTokenStore;

    private static final long EXPIRE_DAYS = 4;

    @Override
    public EmulTokenResponse issueToken(EmulTokenRequest requestDto) {
        String mdn = requestDto.getMdn();
        String token = UUID.randomUUID().toString();

        emulTokenStore.saveToken(mdn, token, EXPIRE_DAYS);

        return EmulTokenResponse.builder()
                .rstCd("000")
                .rstMsg("Success")
                .mdn(mdn)
                .token(token)
                .exPeriod(String.valueOf(EXPIRE_DAYS))
                .build();

    }

}
