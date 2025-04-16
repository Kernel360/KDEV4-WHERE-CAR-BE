package com.wherecar.rest.security.auth;


import com.wherecar.rest.user.domain.User;
import com.wherecar.rest.user.infrastructure.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    // 기본 method 라 username 을 parameter 로 받음
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername");
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isEmpty()) {
            log.info("user not found");
            throw new UsernameNotFoundException("User not found with email: " + username);
        }
        return new CustomUserDetails(user.get());


    }
}
