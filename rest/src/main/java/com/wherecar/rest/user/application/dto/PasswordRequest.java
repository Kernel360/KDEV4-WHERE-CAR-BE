package com.wherecar.rest.user.application.dto;

import lombok.*;

@Setter
@Getter
@ToString
public class PasswordRequest {
    String currentPassword;
    String newPassword;
}
