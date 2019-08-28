package de.home.media.api.artist;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ArtistPost {

    @NotBlank
    @Size(min = 1, max = 100)
    private String name;

    protected ArtistPost() {}

    public void setName(String name) {
        this.name = name;
    }

    public Artist toEntity() {
        Artist entity = new Artist();
        entity.setName(this.name);
        return entity;
    }
}
