package com.wherecar.rest.user.domain;

import com.wherecar.rest.company.domain.Company;
import com.wherecar.rest.user.application.dto.UserRequest;
import com.wherecar.rest.user.application.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserFactory {
    private final PasswordEncoder passwordEncoder;

    public User toUser(UserRequest userRequest, Company company) {
        return User.builder()
                .phone(userRequest.getPhone())
                .email(userRequest.getEmail())
                .name(userRequest.getName())
                .jobTitle(userRequest.getJobTitle())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .company(company)
                .build();
    }

    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .userId(user.getId())
                .name(user.getName())
                .phone(user.getPhone())
                .email(user.getEmail())
                .jobTitle(user.getJobTitle())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public List<UserResponse> toUserResponses(List<User> users) {
        return users.stream()
                .map(this::toUserResponse) // 수정: this::toUserResponse로 메소드 레퍼런스 사용
                .collect(Collectors.toList());
    }
}
