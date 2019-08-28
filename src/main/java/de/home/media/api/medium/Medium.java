package de.home.media.api.medium;

import de.home.media.api.common.BaseEntity;
import de.home.media.api.artist.Artist;
import de.home.media.api.mediumlocation.MediumLocation;

import javax.persistence.*;

@Entity
@Table(name = "MEDIUM")
public class Medium extends BaseEntity {

    @Column(length = 255)
    private String title;

    @Column(length = 255)
    private String description;

    @ManyToOne(optional = false)
    private Artist artist;

    @OneToOne
    @JoinColumn(name = "medium_location_id", referencedColumnName = "id")
    private MediumLocation location;

    public Medium() { }

    public Medium(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne
    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public MediumLocation getLocation() {
        return location;
    }

    public void setLocation(MediumLocation location) {
        this.location = location;
    }
}
