package com.wherecar.rest.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wherecar.rest.common.response.BaseResponse;
import com.wherecar.rest.security.jwt.dto.TokenPair;
import com.wherecar.rest.user.application.dto.UserLoginRequest;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.BufferedReader;

@Slf4j
@AllArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // JSON 요청 바디를 읽어서 파싱
            BufferedReader reader = request.getReader();
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }

            // Jackson을 사용하여 JSON을 UserLoginRequest 객체로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            UserLoginRequest loginRequest = objectMapper.readValue(json.toString(), UserLoginRequest.class);

            String email = loginRequest.getEmail();
            String password = loginRequest.getPassword();

            log.info("Login attempt: " + email);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password, null);

            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            log.error("Error while reading the request or parsing the JSON: " + e.getMessage());
            throw new RuntimeException("Failed to parse authentication request body", e);
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {

        User user  = (User) authentication.getPrincipal();

        String email = user.getUsername();

        TokenPair tokens = tokenService.issueTokens(email);

        response.setHeader("Authorization", "Bearer " + tokens.getAccessToken());
        response.addCookie(CookieUtil.createRefreshTokenCookie(tokens.getRefreshToken()));
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json;charset=UTF-8");

        BaseResponse<TokenPair> successResponse = BaseResponse.ok();

        try {
            response.getWriter().write(successResponse.toString());  // toString 내부에 ObjectMapper 있음!
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        log.info("unsuccessfulAuthentication");

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");

        BaseResponse<?> errorResponse = BaseResponse.unauthorized("아이디 또는 비밀번호가 올바르지 않습니다.");

        try {
            response.getWriter().write(errorResponse.toString());
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }

    }

}
