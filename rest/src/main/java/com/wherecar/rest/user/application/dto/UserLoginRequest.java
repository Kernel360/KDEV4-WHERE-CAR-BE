package com.wherecar.rest.user.application.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserLoginRequest {
    private String email;
    private String password;
}
