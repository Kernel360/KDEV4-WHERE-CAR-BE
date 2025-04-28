package com.wherecar.rest.user.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@ToString
public class PasswordRequest {

    @NotBlank(message = "currentPassword는 필수입니다.")
    String currentPassword;

    @NotBlank(message = "newPassword는 필수입니다.")
    String newPassword;
}
