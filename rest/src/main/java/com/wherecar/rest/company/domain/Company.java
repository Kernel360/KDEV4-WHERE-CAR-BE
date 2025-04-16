package com.wherecar.rest.company.domain;

import com.wherecar.rest.common.domain.BaseEntity;
import com.wherecar.rest.company.application.dto.CompanyRequest;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "companies")
@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Company extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="company_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "website")
    private String website;

    private String description;

    public void updateCompany(CompanyRequest companyRequest){
        this.name = companyRequest.getName();
        this.address = companyRequest.getAddress();
        this.phone = companyRequest.getPhone();
        this.email = companyRequest.getEmail();
        this.website = companyRequest.getWebsite();
        this.description = companyRequest.getDescription();
    }

}
