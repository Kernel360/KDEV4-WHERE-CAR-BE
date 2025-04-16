package com.wherecar.rest.user.infrastructure;

import com.wherecar.rest.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByCompanyId(Long id);

    @Query("SELECT DISTINCT u FROM User u " +
            "LEFT JOIN FETCH u.userPermissions up " +
            "LEFT JOIN FETCH up.permission p " +
            "LEFT JOIN FETCH u.company c " +
            "WHERE u.email = :email")
    Optional<User> findUserWithPermissionsAndCompany(@Param("email") String email);

}
