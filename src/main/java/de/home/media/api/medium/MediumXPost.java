package de.home.media.api.medium;

import de.home.media.api.artist.Artist;
import de.home.media.api.location.Location;
import de.home.media.api.mediumlocation.MediumLocation;

import javax.validation.constraints.*;

public class MediumXPost {

    @NotBlank
    @Size(min = 0, max = 255)
    private String artist;

    @NotBlank
    @Size(min = 0, max = 255)
    private String title;

    @Size(min = 0, max = 255)
    private String description;

    @NotNull
    @Min(0)
    private Integer index;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    @Size(min = 0, max = 50)
    private String location;

    protected MediumXPost() {}

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getArtist() {
        return artist;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getIndex() {
        return index;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public Medium toEntity(Artist artist, MediumLocation location) {
        Medium entity = new Medium();
        entity.setTitle(this.title);
        //entity.setDescription((this.description == null) ? "" : this.description);
        entity.setDescription(this.description);
        entity.setArtist(artist);
        entity.setLocation(location);
        return entity;
    }
}
