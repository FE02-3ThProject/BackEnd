package com.github.gather.entity.Role;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    GUEST("ROLE_GUEST"), USER("ROLE_USER");
    private final String key;
}
