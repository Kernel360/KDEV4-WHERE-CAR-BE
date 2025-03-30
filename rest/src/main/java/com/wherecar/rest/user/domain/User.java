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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserPermission> userPermissions = new HashSet<>();

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

    public void addPermission(Permission permission) {
        if(userPermissions == null) {
            this.userPermissions = new HashSet<>();
        }
        UserPermission userPermission = UserPermission.builder()
                .user(this)
                .permission(permission)
                .build();
        this.userPermissions.add(userPermission);
    }

    public void removePermission(Permission permission) {
        for (UserPermission userPermission : this.userPermissions) {
            if (userPermission.getPermission().equals(permission)) {
                this.userPermissions.remove(userPermission);
                break;
            }
        }
    }

}
