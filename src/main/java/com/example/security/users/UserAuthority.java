package com.example.security.users;

import org.springframework.security.core.GrantedAuthority;

public enum UserAuthority implements GrantedAuthority {
    ADMIN, COORDINATOR, RECRUITER, CANDIDATE;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
