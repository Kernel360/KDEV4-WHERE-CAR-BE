package com.wherecar.rest.user.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserLoginRequest {

    @NotBlank(message = "email은 필수입니다.")
    @Email
    private String email;

    @NotBlank(message = "password는 필수입니다.")
    private String password;
}
