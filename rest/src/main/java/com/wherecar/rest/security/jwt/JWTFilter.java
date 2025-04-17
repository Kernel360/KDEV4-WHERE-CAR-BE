package com.wherecar.rest.security.jwt;

import com.wherecar.rest.user.domain.User;
import com.wherecar.rest.user.infrastructure.UserRepository;
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

        String token = authorization.split(" ")[1];


        if (jwtUtil.isExpired(token)) {
            filterChain.doFilter(request, response);

            return;
        }


        String email = jwtUtil.getEmail(token);


        User user = userRepository.findUserWithPermissionsAndCompany(email).orElseThrow(() -> new NoSuchElementException("User not found"));

        request.setAttribute("userId", user.getId());
        request.setAttribute("companyId", user.getCompany().getId());
        request.setAttribute("permissionTypes", user.getUserPermissions().stream()
                .map(userPermission -> userPermission.getPermission().getType())
                .collect(Collectors.toSet()));

        Authentication authToken = new UsernamePasswordAuthenticationToken(new org.springframework.security.core.userdetails.User(email,"[PROTECTED]",List.of()), null, List.of());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
