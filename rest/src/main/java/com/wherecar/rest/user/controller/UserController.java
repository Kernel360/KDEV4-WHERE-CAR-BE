package com.wherecar.rest.user.controller;

import com.wherecar.rest.user.auth.AuthUtil;
import com.wherecar.rest.user.domain.PermissionType;
import com.wherecar.rest.user.dto.*;
import com.wherecar.rest.user.permissionCheck.RequiredPermission;
import com.wherecar.rest.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/root")
    public ResponseEntity<Void> rootCreate(@RequestBody UserCompanyRequest userCompanyRequest) {
        log.info("Creating root user with company: {}", userCompanyRequest);
        userService.createRoot(userCompanyRequest);
        return ResponseEntity.ok().build();
    }

    @RequiredPermission(PermissionType.SUB_USER_CREATE)
    @PostMapping("/sub")
    public ResponseEntity<Void> subCreate(@RequestBody UserRequest userRequest) {
        log.info("Creating sub user: {}", userRequest);
        Long companyId = AuthUtil.getCompanyId();
        userService.createSub(userRequest, companyId);
        return ResponseEntity.ok().build();
    }
    @RequiredPermission(PermissionType.USER_VIEW)
    @GetMapping("/companies/{companyId}")
    public ResponseEntity<List<UserResponse>> usersGetOfCompany(@PathVariable Long companyId){
        log.info("Retrieving users with company");
        List<UserResponse> userResponses = userService.getUsersOfCompany(companyId);
        return ResponseEntity.ok(userResponses);
    }

    @RequiredPermission(PermissionType.USER_VIEW)
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> userGet(@PathVariable Long userId){
        log.info("Retrieving user with id: {}", userId);
        UserResponse userResponse = userService.getUserById(userId);
        return ResponseEntity.ok(userResponse);
    }

    @RequiredPermission(PermissionType.ROOT)
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> userDelete(@PathVariable Long userId){
        log.info("Deleting user with id: {}", userId);
        userService.deleteUserById(userId);
        return ResponseEntity.ok().build();
    }

    @RequiredPermission(PermissionType.ROOT)
    @PutMapping("/{userId}")
    public ResponseEntity<Void> userUpdate(@PathVariable Long userId, @RequestBody UserRequest userRequest){
        log.info("Updating user with id: {}", userId);
        userService.updateUserById(userId, userRequest);
        return ResponseEntity.ok().build();
    }

    @RequiredPermission(PermissionType.ROOT)
    @PutMapping("/password")
    public ResponseEntity<Void> passwordUpdate(@RequestBody String password){
        log.info("Updating password");
        Long myUserId = AuthUtil.getUserId();
        userService.updatePasswordById(myUserId, password);
        return ResponseEntity.ok().build();
    }

    //Permission


    @RequiredPermission(PermissionType.ROOT)
    @PostMapping("/permission/{userId}")
    public ResponseEntity<Void> permissionAdd(@PathVariable Long userId, @RequestBody PermissionRequest permissionRequest){
        log.info("Adding permission with id: {}", userId);
        userService.addPermission(userId, permissionRequest);
        return ResponseEntity.ok().build();
    }

    @RequiredPermission(PermissionType.ROOT)
    @GetMapping("/permission/{userId}")
    public ResponseEntity<PermissionResponse> permissionGet(@PathVariable Long userId){
        log.info("Retrieving permission with id: {}", userId);
        PermissionResponse permissionResponse = userService.getPermissionById(userId);
        return ResponseEntity.ok(permissionResponse);
    }

    @RequiredPermission(PermissionType.ROOT)
    @DeleteMapping("/permission/{userId}")
    public ResponseEntity<Void> permissionDelete(@PathVariable Long userId, @RequestBody PermissionRequest permissionRequest){
        log.info("Deleting permission with id: {}", userId);
        userService.deletePermissionById(userId, permissionRequest);
        return ResponseEntity.ok().build();
    }
}
