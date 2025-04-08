package com.wherecar.rest.user.service;

import com.wherecar.rest.domain.Company;
import com.wherecar.rest.dto.CompanyRequest;
import com.wherecar.rest.repository.CompanyRepository;
import com.wherecar.rest.user.domain.Permission;
import com.wherecar.rest.user.domain.PermissionType;
import com.wherecar.rest.user.domain.User;
import com.wherecar.rest.user.domain.UserPermission;
import com.wherecar.rest.user.dto.*;
import com.wherecar.rest.user.repository.PermissionRepository;
import com.wherecar.rest.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createRoot(RootUserRequest rootUserRequest) {
        emailExists(rootUserRequest.getUser().getEmail());
        CompanyRequest companyRequest = rootUserRequest.getCompany();
        Company company = Company.builder()
                .phone(companyRequest.getPhone())
                .email(companyRequest.getEmail())
                .name(companyRequest.getName())
                .address(companyRequest.getAddress())
                .website(companyRequest.getWebsite())
                .build();
        companyRepository.save(company);

        UserRequest userRequest = rootUserRequest.getUser();
        User user = this.createUser(userRequest, company);
        Permission rootPermission = permissionRepository.findByType(PermissionType.PERM_ADMIN).orElseThrow();

        user.changeUserPermissions(rootPermission);
        userRepository.save(user);
    }

    @Override
    public void createSub(SubUserRequest subUserRequest, Long companyId) {
        emailExists(subUserRequest.getUser().getEmail());
        Company company = companyRepository.findById(companyId).orElseThrow();
        User user = this.createUser(subUserRequest.getUser(), company);
        this.updatePermission(user.getId(), subUserRequest.getPermission());
    }

    @Override
    @Transactional(readOnly = true)
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
                    .createdAt(user.getCreatedAt())
                    .updatedAt(user.getUpdatedAt())
                    .build();
            userResponses.add(userResponse);
        }
        return userResponses;
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
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

    @Override
    public void deleteUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        userRepository.delete(user);
    }

    @Override
    public void updateUserById(Long userId, UserRequest userRequest) {
        User user = userRepository.findById(userId).orElseThrow();
        user.changeName(userRequest.getName());
        user.changePhone(userRequest.getPhone());
        user.changeEmail(userRequest.getEmail());
        user.changeJobTitle(userRequest.getJobTitle());
        userRepository.save(user);
    }

    @Override
    public void updatePasswordById(Long userId, PasswordRequest passwordRequest) {
        User user = userRepository.findById(userId).orElseThrow();
        if (!passwordEncoder.matches(passwordRequest.getCurrentPassword(), user.getPassword())) {
            user.changePassword(passwordEncoder.encode(passwordRequest.getNewPassword()));
        }
        userRepository.save(user);
    }

    //권한

    @Override
    public void updatePermission(Long userId, PermissionRequest permissionRequest) {
        User user = userRepository.findById(userId).orElseThrow();
        List<Permission> permissions = new ArrayList<>();
        log.info(permissionRequest.toString());
        for(PermissionType permissionType : permissionRequest.getPermissionTypes()){
            Permission permission = permissionRepository.findByType(permissionType).orElseThrow();
            permissions.add(permission);
        }
        user.changeUserPermissions(permissions.toArray(new Permission[0]));
        log.info("Size: {}", user.getUserPermissions().size());
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public PermissionResponse getPermissionById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        Set<UserPermission> userPermission = user.getUserPermissions();
        Set<PermissionType> permissionTypes = new HashSet<>();
        for(UserPermission permission : userPermission){
            permissionTypes.add(permission.getPermission().getType());
        }
        return PermissionResponse.builder()
                .permissionTypes(permissionTypes)
                .build();
    }


    private User createUser(UserRequest userRequest, Company company) {
        User user = User.builder()
                .phone(userRequest.getPhone())
                .email(userRequest.getEmail())
                .name(userRequest.getName())
                .jobTitle(userRequest.getJobTitle())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .company(company)
                .build();
        userRepository.save(user);
        return user;
    }

    public void emailExists(String email) {
        if (userRepository.findByEmail(email).isPresent()){
           throw new RuntimeException("Email already exists");
        }
    }
}
