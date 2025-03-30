package com.wherecar.rest.user.service;

import com.wherecar.rest.domain.Company;
import com.wherecar.rest.dto.CompanyRequest;
import com.wherecar.rest.repository.CompanyRepository;
import com.wherecar.rest.user.domain.User;
import com.wherecar.rest.user.dto.UserCompanyRequest;
import com.wherecar.rest.user.dto.UserRequest;
import com.wherecar.rest.user.dto.UserResponse;
import com.wherecar.rest.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createRoot(UserCompanyRequest userCompanyRequest) {
        CompanyRequest companyRequest = userCompanyRequest.getCompany();
        Company company = Company.builder()
                .phone(companyRequest.getPhone())
                .email(companyRequest.getEmail())
                .name(companyRequest.getName())
                .address(companyRequest.getAddress())
                .website(companyRequest.getWebsite())
                .build();
        companyRepository.save(company);

        UserRequest userRequest = userCompanyRequest.getUser();
        this.createUser(userRequest, company);
    }

    @Override
    public void createSub(UserRequest userRequest, Long companyId) {
        Company company = companyRepository.findById(companyId).orElseThrow();
        this.createUser(userRequest, company);
    }

    @Override
    public List<UserResponse> getUsersOfCompany(Long companyId) {
        List<User> users = userRepository.findByCompanyId(companyId);
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : users) {
            UserResponse userResponse = UserResponse.builder()
                    .userId(user.getId())
                    .name(user.getName())
                    .phone(user.getPhone())
                    .email(user.getEmail())
                    .jobTitle(user.getJobTitle())
                    .build();
            userResponses.add(userResponse);
        }
        return userResponses;
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        return UserResponse.builder()
                .userId(user.getId())
                .name(user.getName())
                .phone(user.getPhone())
                .email(user.getEmail())
                .jobTitle(user.getJobTitle())
                .build();
    }

    @Override
    public void deleteUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        userRepository.delete(user);
    }

    @Override
    public void updateUserById(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id).orElseThrow();
        user.changeName(userRequest.getName());
        user.changePhone(userRequest.getPhone());
        user.changeEmail(userRequest.getEmail());
        user.changeJobTitle(userRequest.getJobTitle());
        userRepository.save(user);
    }

    @Override
    public void updatePasswordById(Long id, String password) {
        User user = userRepository.findById(id).orElseThrow();
        user.changePassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public void createUser(UserRequest userRequest, Company company) {
        User user = User.builder()
                .phone(userRequest.getPhone())
                .email(userRequest.getEmail())
                .name(userRequest.getName())
                .jobTitle(userRequest.getJobTitle())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .company(company)
                .build();
        userRepository.save(user);
    }
}
