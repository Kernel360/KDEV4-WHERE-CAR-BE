package com.wherecar.rest.user.controller;

import com.wherecar.rest.user.dto.UserCompanyRequest;
import com.wherecar.rest.user.dto.UserRequest;
import com.wherecar.rest.user.dto.UserResponse;
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
        log.info("Creating user with company: {}", userCompanyRequest);
        userService.createRoot(userCompanyRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sub")
    public ResponseEntity<Void> subCreate(@RequestBody UserRequest userRequest) {
        log.info("subCreate: {}", userRequest);
        Long myCompanyId = 0L;
        userService.createSub(userRequest, myCompanyId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/my-company")
    public ResponseEntity<List<UserResponse>> usersGetOfMyCompany(){
        Long myCompanyId = 0L;
        List<UserResponse> userResponses = userService.getUsersOfMyCompany(myCompanyId);
        return ResponseEntity.ok(userResponses);
    }

    @GetMapping("/my-info")
    public ResponseEntity<UserResponse> myInfoGet(){
        Long myUserId = 0L;
        UserResponse userResponse = userService.getUserById(myUserId);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> userGet(@PathVariable Long userId){
        UserResponse userResponse = userService.getUserById(userId);
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> userDelete(@PathVariable Long userId){
        userService.deleteUserById(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> userUpdate(@PathVariable Long userId, @RequestBody UserRequest userRequest){
        userService.updateUserById(userId, userRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/password")
    public ResponseEntity<Void> passwordUpdate(@RequestBody String password){
        Long myUserId = 0L;
        userService.updatePasswordById(myUserId, password);
        return ResponseEntity.ok().build();
    }
}
