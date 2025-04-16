package com.wherecar.rest.user.domain;

import com.wherecar.rest.common.domain.BaseEntity;
import com.wherecar.rest.company.domain.Company;
import com.wherecar.rest.user.application.dto.UserRequest;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Table(name="users")
@Entity
@Getter
@ToString(exclude = "company")
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Builder
    public User(Long id, Company company, String name, String email, String password, String phone, String jobTitle) {
        this.id = id;
        this.company = company;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.jobTitle = jobTitle;
        this.userPermissions = new HashSet<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="company_id")
    private Company company;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserPermission> userPermissions;

    private String name;
    private String email;
    private String password;
    private String phone;
    private String jobTitle;


    public void updateUser(UserRequest userRequest) {
        this.name = userRequest.getName();
        this.phone = userRequest.getPhone();
        this.jobTitle = userRequest.getJobTitle();
    }

    public void changePassword(String password) {
        this.password = password;
    }


    public void changeUserPermissions(Set<Permission> permissions) {
        this.userPermissions.clear();
        for(Permission permission : permissions) {
            UserPermission userPermission = UserPermission.builder()
                    .user(this)
                    .permission(permission)
                    .build();
            this.userPermissions.add(userPermission);
        }
    }

}
