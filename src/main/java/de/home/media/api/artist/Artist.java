package de.home.media.api.artist;

import de.home.media.api.common.BaseEntity;
import de.home.media.api.medium.Medium;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ARTIST")
public class Artist extends BaseEntity {

    @Column(length = 255, unique = true)
    private String name;

    /*@OneToMany(
            mappedBy = "artist",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private final List<Medium> media;*/

    public Artist() {
        /*this.media = new ArrayList<>();*/
    }

    public Artist(String name) {
        this();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public List<Medium> getMedia() {
        return media;
    }*/
}
