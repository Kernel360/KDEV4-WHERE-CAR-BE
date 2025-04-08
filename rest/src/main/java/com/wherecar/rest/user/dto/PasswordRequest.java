package com.wherecar.rest.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PasswordRequest {
    String oldPassword;
    String newPassword;
}
