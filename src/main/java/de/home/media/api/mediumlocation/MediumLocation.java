package de.home.media.api.mediumlocation;

import de.home.media.api.common.BaseEntity;
import de.home.media.api.location.Location;
import de.home.media.api.medium.Medium;

import javax.persistence.*;

@Entity
@Table(name = "MEDIUM_LOCATION")
public class MediumLocation extends BaseEntity {

    @Column
    private Integer index;

    @ManyToOne(optional = false)
    private Location location;

//    @OneToOne(mappedBy = "location")
//    private Medium medium;


    public MediumLocation() {
    }

    public MediumLocation(Integer index, Location location) {
        this.index = index;
        this.location = location;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    @ManyToOne
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

//    public Medium getMedium() {
//        return medium;
//    }
//
//    public void setMedium(Medium medium) {
//        this.medium = medium;
//    }
}
