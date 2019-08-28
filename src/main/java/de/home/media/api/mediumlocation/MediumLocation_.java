package de.home.media.api.mediumlocation;

import de.home.media.api.location.Location;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(MediumLocation.class)
public class MediumLocation_ {
    public static volatile SingularAttribute<MediumLocation, Integer> id;
    public static volatile SingularAttribute<MediumLocation, Integer> index;
    public static volatile SingularAttribute<MediumLocation, Location> location;
}
