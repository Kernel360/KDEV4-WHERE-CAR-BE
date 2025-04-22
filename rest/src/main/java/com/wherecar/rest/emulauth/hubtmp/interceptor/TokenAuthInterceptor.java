package com.wherecar.rest.emulauth.hubtmp.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wherecar.rest.emulauth.hubtmp.token.applicatiom.dto.EmulTokenResponse;
import com.wherecar.rest.emulauth.hubtmp.token.applicatiom.TokenValidationResult;
import com.wherecar.rest.emulauth.hubtmp.token.applicatiom.TokenValidationService;
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
        String mdn = request.getParameter("mdn");
        String token = request.getHeader("Token");

        TokenValidationResult result = tokenValidationService.validate(mdn, token);

        if (!TokenValidationResult.SUCCESS.equals(result)) {

            EmulTokenResponse errorResponse = EmulTokenResponse.builder()
                    .rstCd(result.getRstCd())
                    .rstMsg(result.getRstMsg())
                    .mdn(mdn)
                    .build();

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // DTO를 JSON으로 직렬화해서 응답
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
            return false;
        }

        return true;
    }
}

