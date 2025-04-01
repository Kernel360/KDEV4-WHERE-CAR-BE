package com.wherecar.rest.user.dto;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String email;
    private String password;
}
