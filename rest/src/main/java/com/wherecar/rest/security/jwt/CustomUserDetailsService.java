package com.wherecar.rest.security.jwt;


import com.wherecar.rest.user.domain.User;
import com.wherecar.rest.user.infrastructure.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // 기본 method 라 username 을 parameter 로 받음
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("loadUserByUsername");
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), List.of());
    }
}
