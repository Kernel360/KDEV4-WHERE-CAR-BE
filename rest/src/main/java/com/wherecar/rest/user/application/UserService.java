package com.wherecar.rest.user.application;

import com.wherecar.rest.user.application.dto.*;

import java.util.List;

public interface UserService {
    public void createRoot(RootUserRequest rootUserRequest);
    public void createSub(SubUserRequest subUserRequest, Long companyId);
    public List<UserResponse> getUsersOfCompany(Long companyId);
    public UserResponse getUserById(Long userId);
    public void deleteUserById(Long userId);
    public void updateUserById(Long userId, UserRequest userRequest);
    public void updatePasswordById(Long userId, PasswordRequest passwordRequest);

    //permission
    public void updatePermission(Long userId, PermissionRequest permissionRequest);
    public PermissionResponse getPermissionById(Long userId);

}
