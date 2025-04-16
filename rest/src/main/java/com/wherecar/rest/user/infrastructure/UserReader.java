package com.wherecar.rest.user.infrastructure;

import com.wherecar.rest.user.domain.User;

import java.util.List;

public interface UserReader {
    User getUserByEmail(String email);
    List<User> getUsersByCompanyId(Long companyId);
    boolean emailExists(String email);
}
