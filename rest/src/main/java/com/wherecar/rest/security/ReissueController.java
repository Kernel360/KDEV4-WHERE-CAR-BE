package com.wherecar.rest.security;

import com.wherecar.rest.security.jwt.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@AllArgsConstructor
public class ReissueController {

    private final JWTUtil jwtUtil;

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {

        //Todo: 내부 로직 -> 서비스 딴으로 옮길 것

        //get refresh token
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh")) {
                refresh = cookie.getValue();
            }
        }

        if (refresh == null) {
            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST);
        }

        //리프레쉬 토큰 만료
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);
        }

        // 토큰이 refresh인지 확인
        String category = jwtUtil.getCategory(refresh);

        if (!category.equals("refresh")) {
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        String email = jwtUtil.getEmail(refresh);

        String newAccessToken = jwtUtil.createJwt("access", email, 600000L);
        response.setHeader("Authorization", "Bearer " + newAccessToken);

        //rotation 로직
        String newRefreshToken = jwtUtil.createJwt("refresh", email, 86400000L);
        response.addCookie(createCookie(newRefreshToken));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Cookie createCookie(String value) {

        Cookie cookie = new Cookie("refresh", value);
        cookie.setMaxAge(24*60*60);
        //cookie.setSecure(true);
        //cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }
}
