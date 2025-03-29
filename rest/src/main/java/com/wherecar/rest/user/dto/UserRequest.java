package com.wherecar.rest.user.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String jobTitle;
}

