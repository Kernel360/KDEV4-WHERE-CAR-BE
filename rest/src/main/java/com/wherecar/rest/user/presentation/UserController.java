package com.wherecar.rest.user.presentation;

import com.wherecar.rest.common.response.BaseResponse;
import com.wherecar.rest.security.aspect.RequiredPermission;
import com.wherecar.rest.user.application.UserService;
import com.wherecar.rest.user.application.dto.*;
import com.wherecar.rest.user.domain.constant.PermissionType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Todo: 권한 체크 추후 추가 예정
@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/root")
    public ResponseEntity<BaseResponse<UserResponse>> rootCreate(@RequestBody @Valid RootUserRequest rootUserRequest) {
        log.info("Creating root user with company: {}", rootUserRequest);
        UserResponse userResponse = userService.createRoot(rootUserRequest);
        return BaseResponse.created(userResponse);
    }

    @RequiredPermission(PermissionType.PERM_EMPLOYEE_ADD)
    @PostMapping("/sub")
    public ResponseEntity<BaseResponse<UserResponse>> subCreate(HttpServletRequest request, @RequestBody @Valid SubUserRequest subUserRequest) {
        log.info("Creating sub user: {}", subUserRequest);
        Long companyId = (Long)request.getAttribute("companyId");
        UserResponse userResponse = userService.createSub(subUserRequest, companyId);
        return BaseResponse.created(userResponse);
    }
    @RequiredPermission(PermissionType.PERM_EMPLOYEE_VIEW)
    @GetMapping("/companies/my")
    public ResponseEntity<BaseResponse<List<UserResponse>>> usersGetOfCompany(HttpServletRequest request){
        Long companyId = (Long)request.getAttribute("companyId");
        log.info("Retrieving users with company");
        List<UserResponse> userResponses = userService.getUsersOfCompany(companyId);
        return BaseResponse.ok(userResponses);
    }

    @RequiredPermission(PermissionType.PERM_EMPLOYEE_VIEW)
    @GetMapping("/{userId}")
    public ResponseEntity<BaseResponse<UserResponse>> userGet(@PathVariable Long userId){
        log.info("Retrieving user with id: {}", userId);
        UserResponse userResponse = userService.getUserById(userId);
        return BaseResponse.ok(userResponse);
    }

    @GetMapping("/my")
    public ResponseEntity<BaseResponse<UserResponse>> myUserGet(HttpServletRequest request){
        Long userId = (Long)request.getAttribute("userId");
        log.info("Retrieving user with id: {}", userId);
        UserResponse userResponse = userService.getUserById(userId);
        return BaseResponse.ok(userResponse);
    }

    @RequiredPermission(PermissionType.PERM_EMPLOYEE_DELETE)
    @DeleteMapping("/{userId}")
    public ResponseEntity<BaseResponse<Void>> userDelete(@PathVariable Long userId){
        log.info("Deleting user with id: {}", userId);
        userService.deleteUserById(userId);
        return BaseResponse.ok();
    }

    @DeleteMapping("/my")
    public ResponseEntity<BaseResponse<Void>> myUserDelete(HttpServletRequest request){
        Long userId = (Long)request.getAttribute("userId");
        log.info("Deleting user with id: {}", userId);
        userService.deleteUserById(userId);
        return BaseResponse.ok();
    }

    @RequiredPermission(PermissionType.PERM_EMPLOYEE_EDIT)
    @PutMapping("/{userId}")
    public ResponseEntity<BaseResponse<UserResponse>> userUpdate(@PathVariable Long userId, @RequestBody @Valid UserUpdateRequest userRequest){
        log.info("Updating user with id: {}", userId);
        UserResponse userResponse = userService.updateUserById(userId, userRequest);
        return BaseResponse.created(userResponse);
    }

    @PutMapping("/my")
    public ResponseEntity<BaseResponse<UserResponse>> myUserUpdate(HttpServletRequest request, @RequestBody @Valid UserUpdateRequest userRequest){
        Long userId = (Long)request.getAttribute("userId");
        log.info("Updating my user: {}", userId);
        UserResponse userResponse = userService.updateUserById(userId, userRequest);
        return BaseResponse.created(userResponse);
    }

    //Permission


    @RequiredPermission(PermissionType.PERM_PERMISSION_EDIT)
    @PutMapping("/permissions/{userId}")
    public ResponseEntity<BaseResponse<UserResponse>> permissionUpdate(@PathVariable Long userId, @RequestBody PermissionRequest permissionRequest){
        log.info("Adding permission with id: {}", userId);
        UserResponse userResponse = userService.updatePermission(userId, permissionRequest);
        return BaseResponse.created(userResponse);
    }


    @RequiredPermission(PermissionType.PERM_PERMISSION_VIEW)
    @GetMapping("/permissions/{userId}")
    public ResponseEntity<BaseResponse<PermissionResponse>> permissionGet(@PathVariable Long userId){
        log.info("Retrieving permission with id: {}", userId);
        PermissionResponse permissionResponse = userService.getPermissionById(userId);
        return BaseResponse.ok(permissionResponse);
    }

    @GetMapping("/permissions/my")
    public ResponseEntity<BaseResponse<PermissionResponse>> myPermissionGet(HttpServletRequest request){
        Long userId = (Long)request.getAttribute("userId");
        log.info("Retrieving my permission with id: {}", userId);
        PermissionResponse permissionResponse = userService.getPermissionById(userId);
        return BaseResponse.ok(permissionResponse);
    }
}
