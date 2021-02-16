package com.cursor.library.models;

import org.springframework.security.core.GrantedAuthority;

public enum UserPermission {
    ROLE_ADMIN,
    ROLE_LIBRARIAN,
    ROLE_GUEST,
}
