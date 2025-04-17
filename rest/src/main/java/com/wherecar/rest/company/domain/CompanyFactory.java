package com.wherecar.rest.company.domain;

import com.wherecar.rest.company.application.dto.CompanyRequest;
import com.wherecar.rest.company.application.dto.CompanyResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CompanyFactory {
    public Company toCompany(CompanyRequest companyRequest){
        return Company.builder()
                .phone(companyRequest.getPhone())
                .email(companyRequest.getEmail())
                .name(companyRequest.getName())
                .address(companyRequest.getAddress())
                .website(companyRequest.getWebsite())
                .description(companyRequest.getDescription())
                .build();
    }

    public CompanyResponse toCompanyResponse(Company company){
        return CompanyResponse.builder()
                .id(company.getId())
                .address(company.getAddress())
                .email(company.getEmail())
                .name(company.getName())
                .phone(company.getPhone())
                .website(company.getWebsite())
                .description(company.getDescription())
                .build();
    }
}
