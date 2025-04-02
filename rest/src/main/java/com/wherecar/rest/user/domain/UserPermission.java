package com.wherecar.rest.user.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Table(name="user_permissions")
@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_id")
    private Permission permission;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPermission that = (UserPermission) o;
        return user.equals(that.user) && permission.equals(that.permission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, permission);
    }
}
