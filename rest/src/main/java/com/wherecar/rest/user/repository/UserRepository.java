package com.wherecar.rest.user.repository;

import com.wherecar.rest.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByCompanyId(Long id);

    @Query("SELECT u FROM User u " +
            "JOIN FETCH u.userPermissions up " +
            "JOIN FETCH up.permission p " +
            "JOIN FETCH u.company c " +
            "WHERE u.email = :email")
    Optional<User> findUserWithPermissionsAndCompany(@Param("email") String email);
}
