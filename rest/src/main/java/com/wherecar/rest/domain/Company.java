package com.wherecar.rest.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "companies")
@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Company extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="company_id")
    private Long id;

    private String name;
    private String address;
    private String phone;
    private String email;
    private String website;
}
