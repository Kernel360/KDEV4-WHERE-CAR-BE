package com.wherecar.rest.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wherecar.rest.common.constants.ErrorCode;
import com.wherecar.rest.common.response.BaseResponse;
import com.wherecar.rest.user.domain.User;
import com.wherecar.rest.user.infrastructure.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = authorization.substring(7).trim();

        try {
            jwtUtil.isExpired(accessToken);

            // 토큰이 access인지 확인
            String category = jwtUtil.getCategory(accessToken);
            if (!"access".equals(category)) {
                sendErrorResponse(response, new BaseResponse<Void>(ErrorCode.INVALID_TOKEN.toString(), HttpStatus.UNAUTHORIZED.value()));
                return;
            }

            String email = jwtUtil.getEmail(accessToken);

            User user = userRepository.findUserWithPermissionsAndCompany(email)
                    .orElseThrow(() -> new NoSuchElementException("User not found"));

            request.setAttribute("userId", user.getId());
            request.setAttribute("companyId", user.getCompany().getId());
            request.setAttribute("permissionTypes", user.getUserPermissions().stream()
                    .map(userPermission -> userPermission.getPermission().getType())
                    .collect(Collectors.toSet()));

            Authentication authToken = new UsernamePasswordAuthenticationToken(
                    new org.springframework.security.core.userdetails.User(
                            email,
                            "[PROTECTED]",
                            List.of()
                    ),
                    null,
                    List.of()
            );

            SecurityContextHolder.getContext().setAuthentication(authToken);
            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException e) {
            sendErrorResponse(response, new BaseResponse<Void>(ErrorCode.ACCESS_TOKEN_EXPIRED.toString(), HttpStatus.UNAUTHORIZED.value()));
        } catch (JwtException | IllegalArgumentException e) {
            sendErrorResponse(response, new BaseResponse<Void>(ErrorCode.INVALID_TOKEN.toString(), HttpStatus.UNAUTHORIZED.value()));
        } catch (Exception e) {
            sendErrorResponse(response, new BaseResponse<Void>(ErrorCode.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    private void sendErrorResponse(HttpServletResponse response, BaseResponse<?> baseResponse) throws IOException {
        response.setStatus(baseResponse.getStatusCode());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(baseResponse));
    }
}
