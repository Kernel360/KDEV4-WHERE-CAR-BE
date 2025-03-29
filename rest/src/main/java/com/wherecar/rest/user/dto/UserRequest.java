package com.wherecar.rest.user.dto;

import lombok.Data;

@Data
public class UserRequest {
    private Long userId;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String jobTitle;
}

