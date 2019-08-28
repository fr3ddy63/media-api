package de.home.media.api.location;

import de.home.media.api.common.BaseEntity;
import de.home.media.api.mediumlocation.MediumLocation;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "LOCATION")
public class Location extends BaseEntity {

    @Column(length = 255, unique = true)
    private String name;

    /*@OneToMany(
            mappedBy = "location",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private final List<MediumLocation> medialocations = new ArrayList<>();*/

    public Location() { }

    public Location(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public List<MediumLocation> getMediaLocations() {
        return medialocations;
    }*/
}
