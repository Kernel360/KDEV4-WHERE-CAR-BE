package com.wherecar.rest.company.application.dto;


import jakarta.validation.constraints.Email;
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

    private String phone;

    private String website;

    private String description;
}
