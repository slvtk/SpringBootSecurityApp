package ru.kpfu.security.models;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    ROLE_STUDENT,ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
