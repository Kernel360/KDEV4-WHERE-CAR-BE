package com.wherecar.rest.user.infrastructure;

import com.wherecar.rest.user.domain.User;

public interface UserStore {
    public User store(User user);
}
