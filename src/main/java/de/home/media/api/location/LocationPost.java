package de.home.media.api.location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class LocationPost {

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    @Size(min = 0, max = 50)
    private String name;

    protected LocationPost() {}

    public void setName(String name) {
        this.name = name;
    }

    public Location toEntity() {
        Location entity = new Location();
        entity.setName(this.name);
        return entity;
    }
}
