package com.wherecar.collector.domain;

import com.wherecar.rest.domain.BaseEntity;
import com.wherecar.rest.domain.Company;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Table(name="users")
@Entity
@Builder
@ToString(exclude = "company")
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="user_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="company_id")
    private Company company;

    private String name;
    private String email;
    private String password;
    private String phone;
    //직급
    private String jobTitle;

}
