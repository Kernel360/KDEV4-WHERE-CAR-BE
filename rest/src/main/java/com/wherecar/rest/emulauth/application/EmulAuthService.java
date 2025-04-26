package com.wherecar.rest.emulauth.application;

import com.wherecar.rest.emulauth.application.dto.EmulTokenRequest;
import com.wherecar.rest.emulauth.application.dto.EmulTokenResponse;
import org.springframework.stereotype.Service;

public interface EmulAuthService {
    EmulTokenResponse issueToken(EmulTokenRequest requestDto);
}
