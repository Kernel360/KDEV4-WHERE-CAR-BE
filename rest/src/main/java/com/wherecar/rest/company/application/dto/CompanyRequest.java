package com.wherecar.rest.company.application.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Setter
@Getter
@ToString
public class CompanyRequest {

    private String id;

    private String address;

    @Email
    private String email;

    private String name;

    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다.")
    private String phone;

    private String website;

    private String description;
}
