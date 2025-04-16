package com.wherecar.rest.company.application.dto;


import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRequest {
    private String id;
    private String address;
    private String email;
    private String name;
    private String phone;
    private String website;
    private String description;
}
