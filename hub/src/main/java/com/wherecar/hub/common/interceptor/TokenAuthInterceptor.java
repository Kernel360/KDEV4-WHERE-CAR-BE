package com.wherecar.hub.common.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wherecar.hub.token.application.TokenValidationResult;
import com.wherecar.hub.token.application.TokenValidationService;
import com.wherecar.hub.token.application.dto.EmulTokenResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TokenAuthInterceptor implements HandlerInterceptor {

    private final TokenValidationService tokenValidationService;
    private final ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException, IOException {
        String token = request.getHeader("Token");

        TokenValidationResult result = tokenValidationService.validate(token);

        if (!TokenValidationResult.SUCCESS.equals(result)) {

            EmulTokenResponse errorResponse = EmulTokenResponse.builder()
                    .rstCd(result.getRstCd())
                    .rstMsg(result.getRstMsg())
                    .mdn(null)
                    .build();

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
            return false;
        }
        return true;

    }
}

