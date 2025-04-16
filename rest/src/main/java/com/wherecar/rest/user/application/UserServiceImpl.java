package com.wherecar.rest.user.application;


import com.wherecar.rest.company.application.dto.CompanyRequest;
import com.wherecar.rest.company.domain.Company;
import com.wherecar.rest.company.domain.CompanyFactory;
import com.wherecar.rest.company.infrastructure.CompanyReader;
import com.wherecar.rest.company.infrastructure.CompanyStore;
import com.wherecar.rest.user.application.dto.*;
import com.wherecar.rest.user.domain.Permission;
import com.wherecar.rest.user.domain.UserFactory;
import com.wherecar.rest.user.domain.constant.PermissionType;
import com.wherecar.rest.user.domain.User;
import com.wherecar.rest.user.domain.UserPermission;
import com.wherecar.rest.user.infrastructure.UserReader;

import com.wherecar.rest.user.infrastructure.UserStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserFactory userFactory;
    private final CompanyFactory companyFactory;

    private final UserStore userStore;
    private final UserReader userReader;

    private final CompanyStore companyStore;
    private final CompanyReader companyReader;

    private final Map<PermissionType,Permission> permissions;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void createRoot(RootUserRequest rootUserRequest) {

        // 1. 이메일 중복확인
        emailExists(rootUserRequest.getUser().getEmail());

        // 2. 회사 생성
        CompanyRequest companyRequest = rootUserRequest.getCompany();

        Company company = companyFactory.toCompany(companyRequest);
        companyStore.Store(company);

        // 3. 유저 생성
        UserRequest userRequest = rootUserRequest.getUser();
        User user = userFactory.toUser(userRequest, company);

        // 4. 유저 권한 지정
        Permission rootPermission = permissions.get(PermissionType.PERM_ADMIN);
        user.changeUserPermissions(rootPermission);

        //5. 저장
        user = userStore.store(user);
    }

    @Override
    public void createSub(SubUserRequest subUserRequest, Long companyId) {





        // 1. 이메일 중복확인
        userReader.dddddd;

        // 2. 회사 조회
        Company company = companyReader.getById(companyId);

        // 3. 유저 생성
        UserRequest userRequest = subUserRequest.getUser();
        User user = userFactory.toUser(userRequest, company);

        // 4. 유저 권한 지정


        // 5. 전체 저장

    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getUsersOfCompany(Long companyId) {
        // 1. 유저조회
        List<User> users = userRepository.findByCompanyId(companyId);

        // 2. userResponse 로 전환

        return users.stream()
                .map(userFactory::toUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return userFactory.toUserResponse(user);
    }

    @Override
    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public void updateUserById(Long userId, UserRequest userRequest) {
        User user = userRepository.findById(userId).orElseThrow();
        user.updateUser(userRequest);
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



}
