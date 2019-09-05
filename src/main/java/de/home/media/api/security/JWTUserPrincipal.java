package de.home.media.api.security;

import java.security.Principal;
import java.util.Set;

public class JWTUserPrincipal implements Principal {

    private final String name;
    private final Set<String> roles;

    public JWTUserPrincipal(String name, Set<String> roles) {
        this.name = name;
        this.roles = roles;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public Set<String> getRoles() {
        return this.roles;
    }
}
