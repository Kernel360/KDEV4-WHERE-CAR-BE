package com.wherecar.rest.user.domain;

import com.wherecar.rest.domain.BaseEntity;
import com.wherecar.rest.domain.Company;
import jakarta.persistence.*;
import lombok.*;


@Table(name="users")
@Entity
@Getter
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
    private String jobTitle;

}
