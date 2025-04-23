package com.wherecar.rest.security.jwt;

import com.wherecar.rest.common.response.BaseResponse;
import com.wherecar.rest.user.domain.User;
import com.wherecar.rest.user.infrastructure.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization= request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = authorization.substring(7).trim();


        try {
            jwtUtil.isExpired(accessToken);
        } catch (ExpiredJwtException e) {
            sendErrorResponse(response, BaseResponse.unauthorized("토큰이 만료되었습니다."));
            return;
        }

        // 토큰이 access인지 확인
        String category = jwtUtil.getCategory(accessToken);
        if (!"access".equals(category)) {
            sendErrorResponse(response, BaseResponse.unauthorized("유효하지 않은 엑세스 토큰입니다."));
            return;
        }

        String email = jwtUtil.getEmail(accessToken);

        User user = userRepository.findUserWithPermissionsAndCompany(email).orElseThrow(() -> new NoSuchElementException("User not found"));

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
    }

    private void sendErrorResponse(HttpServletResponse response, BaseResponse<?> baseResponse) throws IOException {
        response.setStatus(baseResponse.getStatusCode());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(baseResponse.toString());
    }

}
