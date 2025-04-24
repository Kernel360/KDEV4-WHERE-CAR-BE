package com.wherecar.rest.emulauth.infrastructure;

public interface EmulTokenStore {
    void saveToken(String mdn, String token, long expireSeconds);
}
