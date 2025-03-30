package com.wherecar.rest.user.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name="user_permissions")
@Entity
@Getter
@Builder
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
}
