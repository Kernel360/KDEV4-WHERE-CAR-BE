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
        return userFactory.toUserResponse(user);
    }

    @Override
    public UserResponse createSub(SubUserRequest subUserRequest, Long companyId) {

        // 0. 필요한 파라미터 준비
        UserRequest userRequest = subUserRequest.getUser();
        Set<PermissionType> permissionTypes = subUserRequest.getPermission().getPermissionTypes();


        // 1. 이메일 중복확인
        checkEmailDuplication(userRequest.getEmail());

        // 2. 회사 조회
        Company company = companyReader.getById(companyId);

        // 3. 유저 생성
        User user = userFactory.toUser(userRequest, company);

        // 4. 유저 권한 지정 및 저장
        user = userStore.store(user, permissionTypes);

        // 5. 유저 dto 반환
        return userFactory.toUserResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getUsersOfCompany(Long companyId) {
        // 1. 유저조회
        List<User> users = userReader.getUsersByCompanyId(companyId);

        // 2. 유저 dto 리스트로 반환
        return users.stream()
                .map(userFactory::toUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long userId) {
        User user = userReader.getUserById(userId);
        return userFactory.toUserResponse(user);
    }

    @Override
    public void deleteUserById(Long userId) {
        userStore.deleteById(userId);
    }

    @Override
    public UserResponse updateUserById(Long userId, UserRequest userRequest) {
        User user = userReader.getUserById(userId);
        user.updateUser(userRequest);
        userStore.store(user);
        return userFactory.toUserResponse(user);
    }

    @Override
    public UserResponse updatePasswordById(Long userId, PasswordRequest passwordRequest) {
        User user = userReader.getUserById(userId);

        //비밀번호 확인 로직 실행
        if (!passwordEncoder.matches(passwordRequest.getCurrentPassword(), user.getPassword())) {
            user.changePassword(passwordEncoder.encode(passwordRequest.getNewPassword()));
        }
        // 유저 저장
        userStore.store(user);

        return userFactory.toUserResponse(user);
    }

    //권한

    @Override
    public UserResponse updatePermission(Long userId, PermissionRequest permissionRequest) {
        User user = userReader.getUserById(userId);
        Set<PermissionType> permissionTypes = permissionRequest.getPermissionTypes();

        user = userStore.store(user,permissionTypes);
        return userFactory.toUserResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public PermissionResponse getPermissionById(Long userId) {
        User user = userReader.getUserById(userId);

        Set<PermissionType> permissionTypes = user.getUserPermissions().stream()
                .map(userPermission -> userPermission.getPermission().getType())
                .collect(Collectors.toSet());

        return new PermissionResponse(permissionTypes);
    }

    private void checkEmailDuplication(String email){
        if(userReader.emailExists(email)) {
            throw new RuntimeException("Email already exists");
        }
    }
}
