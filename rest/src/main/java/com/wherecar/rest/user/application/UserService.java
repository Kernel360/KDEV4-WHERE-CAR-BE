package com.wherecar.rest.user.application;

import com.wherecar.rest.user.application.dto.*;

import java.util.List;

public interface UserService {
    UserResponse createRoot(RootUserRequest rootUserRequest);
    UserResponse createSub(SubUserRequest subUserRequest, Long companyId);
    List<UserResponse> getUsersOfCompany(Long companyId);
    UserResponse getUserById(Long userId);
    void deleteUserById(Long userId);
    UserResponse updateUserById(Long userId, UserRequest userRequest);
    UserResponse updatePasswordById(Long userId, PasswordRequest passwordRequest);

    //permission
    UserResponse updatePermission(Long userId, PermissionRequest permissionRequest);
    PermissionResponse getPermissionById(Long userId);

}
