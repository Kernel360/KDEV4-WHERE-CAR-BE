package com.wherecar.rest.user.domain;

import com.wherecar.rest.domain.BaseEntity;
import com.wherecar.rest.domain.Company;
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



    public void changeName(String newName) {
        this.name = newName;
    }
    public void changeEmail(String newEmail) {
        this.email = newEmail;
    }
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }
    public void changePhone(String newPhone) {
        this.phone = newPhone;
    }
    public void changeJobTitle(String newJobTitle) {
        this.jobTitle = newJobTitle;
    }


    public void changeUserPermissions(Permission... newUserPermissions) {
        for(UserPermission userPermission : this.userPermissions) {
            this.userPermissions.remove(userPermission);
        }
        for(Permission permission : newUserPermissions) {
            UserPermission userPermission = UserPermission.builder()
                    .user(this)
                    .permission(permission)
                    .build();
            this.userPermissions.add(userPermission);
        }
    }

}
