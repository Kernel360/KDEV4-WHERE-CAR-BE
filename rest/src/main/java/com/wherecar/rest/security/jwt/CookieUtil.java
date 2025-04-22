package com.wherecar.rest.security.jwt;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import static com.wherecar.rest.security.jwt.TokenService.REFRESH_TTL_MS;

public class CookieUtil {

    // 쿠키 생성
    public static Cookie createCookie(String name, String value, String path, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        return cookie;
    }

    public static Cookie createRefreshTokenCookie(String value) {
        return createCookie("refreshToken", value, "/", (int) REFRESH_TTL_MS);
    }

    public static Cookie createExpiredRefreshTokenCookie() {
        return createCookie("refreshToken", null, "/", 0);
    }

    // 쿠키에서 값 추출
    public static String extractCookieValue(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;
        for (Cookie cookie : cookies) {
            if (key.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

}
