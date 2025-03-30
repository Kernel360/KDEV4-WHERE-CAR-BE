package com.wherecar.rest.user.service;

import com.wherecar.rest.user.dto.UserCompanyRequest;
import com.wherecar.rest.user.dto.UserRequest;
import com.wherecar.rest.user.dto.UserResponse;

import java.util.List;

public interface UserService {
    public void createRoot(UserCompanyRequest userCompanyRequest);
    public void createSub(UserRequest userRequest, Long companyId);
    public List<UserResponse> getUsersOfCompany(Long companyId);
    public UserResponse getUserById(Long id);
    public void deleteUserById(Long id);
    public void updateUserById(Long id, UserRequest userRequest);
    public void updatePasswordById(Long id, String password);
}
