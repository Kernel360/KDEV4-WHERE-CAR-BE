package com.wherecar.rest.emulauth.application;

import com.wherecar.rest.emulauth.application.dto.EmulTokenRequestDto;
import com.wherecar.rest.emulauth.application.dto.EmulTokenResponseDto;
import com.wherecar.rest.emulauth.infrastructure.EmulTokenStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmulAuthServiceImpl implements EmulAuthService{

    private final EmulTokenStore emulTokenStore;

    private static final long EXPIRE_DAYS = 4; // 3일 = 259200초

    @Override
    public EmulTokenResponseDto issueToken(EmulTokenRequestDto requestDto) {
        String mdn = requestDto.getMdn();
        String token = UUID.randomUUID().toString();

        emulTokenStore.saveToken(mdn, token, EXPIRE_DAYS);

        return EmulTokenResponseDto.builder()
                .rstCd("000")
                .rstMsg("Success")
                .mdn(mdn)
                .token(token)
                .exPeriod(String.valueOf(EXPIRE_DAYS))
                .build();

    }

}
