package com.wherecar.rest.user.presentation;

import com.wherecar.rest.user.application.dto.*;
import com.wherecar.rest.user.application.UserService;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<Void> rootCreate(@RequestBody RootUserRequest rootUserRequest) {
        log.info("Creating root user with company: {}", rootUserRequest);
        userService.createRoot(rootUserRequest);
        return ResponseEntity.ok().build();
    }

//    @RequiredPermission(PermissionType.SUB_USER_CREATE)
    @PostMapping("/sub")
    public ResponseEntity<Void> subCreate(HttpServletRequest request, @RequestBody SubUserRequest subUserRequest) {
        log.info("Creating sub user: {}", subUserRequest);
        Long companyId = (Long)request.getAttribute("companyId");
        userService.createSub(subUserRequest, companyId);
        return ResponseEntity.ok().build();
    }
//    @RequiredPermission(PermissionType.USER_VIEW)
    @GetMapping("/companies/my")
    public ResponseEntity<List<UserResponse>> usersGetOfCompany(HttpServletRequest request){
        Long companyId = (Long)request.getAttribute("companyId");
        log.info("Retrieving users with company");
        List<UserResponse> userResponses = userService.getUsersOfCompany(companyId);
        return ResponseEntity.ok(userResponses);
    }

//    @RequiredPermission(PermissionType.USER_VIEW)
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> userGet(@PathVariable Long userId){
        log.info("Retrieving user with id: {}", userId);
        UserResponse userResponse = userService.getUserById(userId);
        return ResponseEntity.ok(userResponse);
    }

//    @RequiredPermission(PermissionType.USER_VIEW)
    @GetMapping("/my")
    public ResponseEntity<UserResponse> myUserGet(HttpServletRequest request){
        Long userId = (Long)request.getAttribute("userId");
        log.info("Retrieving user with id: {}", userId);
        UserResponse userResponse = userService.getUserById(userId);
        return ResponseEntity.ok(userResponse);
    }

//    @RequiredPermission(PermissionType.ROOT)
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> userDelete(@PathVariable Long userId){
        log.info("Deleting user with id: {}", userId);
        userService.deleteUserById(userId);
        return ResponseEntity.ok().build();
    }

//    @RequiredPermission(PermissionType.ROOT)
    @DeleteMapping("/my")
    public ResponseEntity<Void> myUserDelete(HttpServletRequest request){
        Long userId = (Long)request.getAttribute("userId");
        log.info("Deleting user with id: {}", userId);
        userService.deleteUserById(userId);
        return ResponseEntity.ok().build();
    }

//    @RequiredPermission(PermissionType.ROOT)
    @PutMapping("/{userId}")
    public ResponseEntity<Void> userUpdate(@PathVariable Long userId, @RequestBody UserRequest userRequest){
        log.info("Updating user with id: {}", userId);
        userService.updateUserById(userId, userRequest);
        return ResponseEntity.ok().build();
    }

//    @RequiredPermission(PermissionType.ROOT)
    @PutMapping("/my")
    public ResponseEntity<Void> myUserUpdate(HttpServletRequest request, @RequestBody UserRequest userRequest){
        Long userId = (Long)request.getAttribute("userId");
        log.info("Updating my user: {}", userId);
        userService.updateUserById(userId, userRequest);
        return ResponseEntity.ok().build();
    }

//    @RequiredPermission(PermissionType.ROOT)
    @PutMapping("/password")
    public ResponseEntity<Void> passwordUpdate(HttpServletRequest request, @RequestBody PasswordRequest passwordRequest){
        Long userId = (Long)request.getAttribute("userId");
        log.info("Updating password");
        userService.updatePasswordById(userId, passwordRequest);
        return ResponseEntity.ok().build();
    }

    //Permission


//    @RequiredPermission(PermissionType.ROOT)
    @PutMapping("/permissions/{userId}")
    public ResponseEntity<Void> permissionUpdate(@PathVariable Long userId, @RequestBody PermissionRequest permissionRequest){
        log.info("Adding permission with id: {}", userId);
        userService.updatePermission(userId, permissionRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/permissions/my")
    public ResponseEntity<Void> myPermissionUpdate(HttpServletRequest request, @RequestBody PermissionRequest permissionRequest){
        Long userId = (Long)request.getAttribute("userId");
        log.info("Retrieving my permission with id: {}", userId);
        userService.updatePermission(userId, permissionRequest);
        return ResponseEntity.ok().build();
    }

//    @RequiredPermission(PermissionType.ROOT)
    @GetMapping("/permissions/{userId}")
    public ResponseEntity<PermissionResponse> permissionGet(@PathVariable Long userId){
        log.info("Retrieving permission with id: {}", userId);
        PermissionResponse permissionResponse = userService.getPermissionById(userId);
        return ResponseEntity.ok(permissionResponse);
    }

    @GetMapping("/permissions/my")
    public ResponseEntity<PermissionResponse> myPermissionGet(HttpServletRequest request){
        Long userId = (Long)request.getAttribute("userId");
        log.info("Retrieving my permission with id: {}", userId);
        PermissionResponse permissionResponse = userService.getPermissionById(userId);
        return ResponseEntity.ok(permissionResponse);
    }
}
