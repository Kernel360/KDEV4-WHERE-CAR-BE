package com.wherecar.rest.security.jwt;

import com.wherecar.rest.common.exception.TokenValidationException;
import com.wherecar.rest.security.jwt.dto.TokenPair;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseBody
@AllArgsConstructor
@RequestMapping("/token")
public class TokenController {

    private final JWTUtil jwtUtil;
    private final TokenService tokenService;

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {

        String refreshToken = CookieUtil.extractCookieValue(request, "refreshToken");
        if (refreshToken == null) {
            return new ResponseEntity<>("No refresh token in cookies", HttpStatus.BAD_REQUEST);
        }

        tokenService.removeRefreshToken(refreshToken);
        response.addCookie(CookieUtil.createExpiredRefreshTokenCookie());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }




    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {

        String refreshToken = CookieUtil.extractCookieValue(request, "refreshToken");
        if (refreshToken == null) {
            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST);
        }

        try {
            tokenService.validateRefreshToken(refreshToken);

            String email = jwtUtil.getEmail(refreshToken);
            TokenPair tokens = tokenService.issueTokens(email);

            response.setHeader("Authorization", "Bearer " + tokens.getAccessToken());
            response.addCookie(CookieUtil.createRefreshTokenCookie(tokens.getRefreshToken()));

            return ResponseEntity.ok().build();

        } catch (TokenValidationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

    }

}
