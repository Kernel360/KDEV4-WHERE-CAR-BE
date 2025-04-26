package com.wherecar.rest.user.application.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserRequest {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String jobTitle;
}

