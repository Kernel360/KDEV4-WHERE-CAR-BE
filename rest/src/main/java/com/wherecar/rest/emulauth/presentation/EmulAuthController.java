package com.wherecar.rest.emulauth.presentation;


import com.wherecar.rest.emulauth.application.EmulAuthService;
import com.wherecar.rest.emulauth.application.dto.EmulTokenRequestDto;
import com.wherecar.rest.emulauth.application.dto.EmulTokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emulator")
@RequiredArgsConstructor
public class EmulAuthController {

    private final EmulAuthService emulAuthService;

    @PostMapping("/token")
    public ResponseEntity<EmulTokenResponseDto> getToken(@RequestBody EmulTokenRequestDto requestDto) {
        EmulTokenResponseDto response = emulAuthService.issueToken(requestDto);
        return ResponseEntity.ok(response);
    }

}
