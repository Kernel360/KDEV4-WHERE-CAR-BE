package com.wherecar.rest.user.service;

import com.wherecar.rest.user.dto.*;

import java.util.List;

public interface UserService {
    public void createRoot(UserCompanyRequest userCompanyRequest);
    public void createSub(UserRequest userRequest, Long companyId);
    public List<UserResponse> getUsersOfCompany(Long companyId);
    public UserResponse getUserById(Long userId);
    public void deleteUserById(Long userId);
    public void updateUserById(Long userId, UserRequest userRequest);
    public void updatePasswordById(Long userId, String password);

    //permission
    public void addPermission(Long userId, PermissionRequest permissionRequest);
    public PermissionResponse getPermissionById(Long userId);
    public void deletePermissionById(Long userId, PermissionRequest permissionRequest);

}
