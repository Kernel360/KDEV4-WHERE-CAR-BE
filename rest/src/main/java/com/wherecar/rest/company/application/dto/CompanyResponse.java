package com.wherecar.rest.company.application.dto;

import lombok.*;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyResponse {

    private Long id;
    private String address;
    private String email;
    private String name;
    private String phone;
    private String website;
    private String description;
}
