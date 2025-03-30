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
    public void updatePasswordById(Long userId, String password) {
        User user = userRepository.findById(userId).orElseThrow();
        user.changePassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    //권한

    @Override
    public void addPermission(Long userId, PermissionRequest permissionRequest) {
        User user = userRepository.findById(userId).orElseThrow();
        for(PermissionType permissionType : permissionRequest.getPermissionTypes()){
            Permission permission = permissionRepository.findByType(permissionType).orElseThrow();
            user.addPermission(permission);
        }
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

    @Override
    public void deletePermissionById(Long userId, PermissionRequest permissionRequest) {
        User user = userRepository.findById(userId).orElseThrow();
        for(PermissionType permissionType : permissionRequest.getPermissionTypes()){
            Permission permission = permissionRepository.findByType(permissionType).orElseThrow();
            user.removePermission(permission);
        }
        userRepository.save(user);
    }

    private void createUser(UserRequest userRequest, Company company) {
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

    private void companyCheck(Long companyIdA, Long companyIdB){
        if(companyIdA!=null && companyIdB!=null){
            if(!companyIdA.equals(companyIdB)){
                throw new RuntimeException("Not same company");
            }
        }
    }

}
