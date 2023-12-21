package com.github.gather.WebSocket;

import java.security.Principal;

public class UserPrincipal implements Principal {

    private final String email;

    public UserPrincipal(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getName() {
        return email;
    }
}
