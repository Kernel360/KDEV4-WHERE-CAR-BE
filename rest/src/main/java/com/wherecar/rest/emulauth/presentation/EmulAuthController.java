package com.wherecar.rest.emulauth.presentation;


import com.wherecar.rest.emulauth.application.EmulAuthService;
import com.wherecar.rest.emulauth.application.dto.EmulTokenRequest;
import com.wherecar.rest.emulauth.application.dto.EmulTokenResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/emulator")
@RequiredArgsConstructor
public class EmulAuthController {

    private final EmulAuthService emulAuthService;

    @PostMapping("/token")
    public ResponseEntity<EmulTokenResponse> getToken(@RequestBody @Valid EmulTokenRequest requestDto) {
        EmulTokenResponse response = emulAuthService.issueToken(requestDto);
        return ResponseEntity.ok(response);
    }

}
