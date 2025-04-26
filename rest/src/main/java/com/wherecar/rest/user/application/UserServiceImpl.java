package com.wherecar.rest.user.application;


import com.wherecar.rest.company.application.dto.CompanyRequest;
import com.wherecar.rest.company.domain.Company;
import com.wherecar.rest.company.domain.CompanyFactory;
import com.wherecar.rest.company.infrastructure.CompanyReader;
import com.wherecar.rest.company.infrastructure.CompanyStore;
import com.wherecar.rest.user.application.dto.*;
import com.wherecar.rest.user.domain.User;
import com.wherecar.rest.user.domain.UserFactory;
import com.wherecar.rest.user.domain.constant.PermissionType;
import com.wherecar.rest.user.infrastructure.UserReader;
import com.wherecar.rest.user.infrastructure.UserStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
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

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createRoot(RootUserRequest rootUserRequest) {
        log.info("[USER][UserServiceImpl][createRoot] 시작 | rootUserRequest={}", rootUserRequest);

        // 0. 필요한 파라미터 준비
        UserRequest userRequest = rootUserRequest.getUser();
        CompanyRequest companyRequest = rootUserRequest.getCompany();

        // 1. 이메일 중복확인
        checkEmailDuplication(userRequest.getEmail());


        // 2. 회사 생성
        Company company = companyFactory.toCompany(companyRequest);
        companyStore.store(company);

        // 3. 유저 생성
        User user = userFactory.toUser(userRequest, company);

        // 4. 유저 권한 지정 및 저장
        user = userStore.store(user, Set.of(PermissionType.PERM_ADMIN));

        // 5. 유저 dto 반환
        UserResponse userResponse = userFactory.toUserResponse(user);

        log.info("[USER][UserServiceImpl][createRoot] 끝 | userResponse={}", userResponse);
        return userResponse;
    }

    @Override
    public UserResponse createSub(SubUserRequest subUserRequest, Long companyId) {
        log.info("[USER][UserServiceImpl][createSub] 시작 | subUserRequest={}, companyId={}", subUserRequest, companyId);

        // 0. 필요한 파라미터 준비
        UserRequest userRequest = subUserRequest.getUser();
        Set<PermissionType> permissionTypes = subUserRequest.getPermission().getPermissionTypes();


        // 1. 이메일 중복확인
        checkEmailDuplication(userRequest.getEmail());

        // 2. 회사 조회
        Company company = companyReader.getCompanyById(companyId);

        // 3. 유저 생성
        User user = userFactory.toUser(userRequest, company);

        // 4. 유저 권한 지정 및 저장
        user = userStore.store(user, permissionTypes);

        // 5. 유저 dto 반환
        UserResponse userResponse = userFactory.toUserResponse(user);

        log.info("[USER][UserServiceImpl][createSub] 끝 | userResponse={}", userResponse);

        return userResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getUsersOfCompany(Long companyId) {
        log.info("[USER][UserServiceImpl][getUsersOfCompany] 시작 | companyId={}", companyId);
        // 1. 유저조회
        List<User> users = userReader.getUsersByCompanyId(companyId);

        // 2. 유저 dto 리스트로 반환
        List<UserResponse> userResponses = userFactory.toUserResponses(users);

        log.info("[USER][UserServiceImpl][getUsersOfCompany] 끝 | userResponses={}", userResponses);
        return userResponses;
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long userId) {
        log.info("[USER][UserServiceImpl][getUserById] 시작 | userId={}", userId);
        User user = userReader.getUserById(userId);
        UserResponse userResponse = userFactory.toUserResponse(user);
        log.info("[USER][UserServiceImpl][getUserById] 끝 | userResponses={}", userResponse);
        return userResponse;
    }

    @Override
    public void deleteUserById(Long userId) {
        log.info("[USER][UserServiceImpl][deleteUserById] 시작 | userId={}", userId);
        userStore.delete(userId);
        log.info("[USER][UserServiceImpl][deleteUserById] 끝");
    }

    @Override
    public UserResponse updateUserById(Long userId, UserRequest userRequest) {
        log.info("[USER][UserServiceImpl][updateUserById] 시작 | userId={}, userRequest={}", userId, userRequest);
        User user = userReader.getUserById(userId);
        user.updateUser(userRequest);
        userStore.store(user);
        UserResponse userResponse = userFactory.toUserResponse(user);
        log.info("[USER][UserServiceImpl][updateUserById] 끝 | userResponse={}", userResponse);
        return userResponse;
    }

    @Override
    public UserResponse updatePasswordById(Long userId, PasswordRequest passwordRequest) {
        log.info("[USER][UserServiceImpl][updatePasswordById] 시작 | userId={}, passwordRequest={}", userId, passwordRequest);
        User user = userReader.getUserById(userId);

        //비밀번호 확인 로직 실행
        if (!passwordEncoder.matches(passwordRequest.getCurrentPassword(), user.getPassword())) {
            user.changePassword(passwordEncoder.encode(passwordRequest.getNewPassword()));
        }
        // 유저 저장
        userStore.store(user);
        UserResponse userResponse = userFactory.toUserResponse(user);
        log.info("[USER][UserServiceImpl][updatePasswordById] 끝 | userResponse={}", userResponse);
        return userResponse;
    }

    //권한

    @Override
    public UserResponse updatePermission(Long userId, PermissionRequest permissionRequest) {
        log.info("[USER][UserServiceImpl][updatePermission] 시작 | userId={}, permissionRequest={}", userId, permissionRequest);
        User user = userReader.getUserById(userId);
        Set<PermissionType> permissionTypes = permissionRequest.getPermissionTypes();

        user = userStore.store(user,permissionTypes);
        UserResponse userResponse = userFactory.toUserResponse(user);
        log.info("[USER][UserServiceImpl][updatePermission] 끝 | userResponse={}", userResponse);
        return userResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public PermissionResponse getPermissionById(Long userId) {
        log.info("[USER][UserServiceImpl][getPermissionById] 시작 | userId={}", userId);
        User user = userReader.getUserById(userId);

        Set<PermissionType> permissionTypes = user.getUserPermissions().stream()
                .map(userPermission -> userPermission.getPermission().getType())
                .collect(Collectors.toSet());

        PermissionResponse permissionResponse  = new PermissionResponse(permissionTypes);
        log.info("[USER][UserServiceImpl][getPermissionById] 끝 | permissionResponse={}", permissionResponse);
        return permissionResponse;
    }

    private void checkEmailDuplication(String email){
        if(userReader.emailExists(email)) {
            throw new RuntimeException("Email already exists");
        }
    }
}
