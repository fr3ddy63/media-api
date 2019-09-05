package de.home.media.api.security;

import de.home.media.api.annotations.Name;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CredentialPost {

    @Name
    @NotNull
    private String name;

    @NotBlank
    private String password;

    public CredentialPost() {
    }

    public CredentialPost(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
