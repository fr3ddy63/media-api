package de.home.media.api.user;

import de.home.media.api.annotations.Name;
import de.home.media.api.group.Group;
import de.home.media.api.security.PasswordHash;

import javax.validation.constraints.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserPost {

    @Name
    @NotNull
    private String name;

    @Email
    @NotNull
    @Size(max = 50)
    private String email;

    // @Pattern(regexp = "^[a-z0-9]{3,}@[a-z0-9]{3,}\\.[a-z]{2,3}$")
    // more sophisticated
    // (?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])

    @NotBlank
    @Size(min = 5, max = 50)
    private String password;

    @NotEmpty
    private Set<String> groups;

    public UserPost() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGroups(Set<String> groups) {
        this.groups = groups;
    }

    public User toEntity(List<Group> validGroups) throws InvalidKeySpecException, NoSuchAlgorithmException {
        PasswordHash hash = new PasswordHash(this.password, 1024, 128);

        User user = new User();
        user.setName(this.name);
        user.setEmail(this.email);
        user.setPassword(hash.createDatabasePassword());
        user.setGroups(validGroups.stream()
                .filter(group -> this.groups.contains(group.getName()))
                .collect(Collectors.toSet()));
        return user;
    }
}
