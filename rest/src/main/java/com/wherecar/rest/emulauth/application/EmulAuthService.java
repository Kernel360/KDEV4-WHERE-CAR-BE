package com.wherecar.rest.emulauth.application;

import com.wherecar.rest.emulauth.application.dto.EmulTokenRequestDto;
import com.wherecar.rest.emulauth.application.dto.EmulTokenResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface EmulAuthService {
    EmulTokenResponseDto issueToken(EmulTokenRequestDto requestDto);
}
