package com.wherecar.rest.user.application.dto;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String email;
    private String password;
}
