package de.home.media.api.location;

import de.home.media.api.mediumlocation.MediumLocation;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Location.class)
public class Location_ {
    public static volatile SingularAttribute<Location, Integer> id;
    public static volatile SingularAttribute<Location, String> name;
    /*public static volatile ListAttribute<Location, MediumLocation> locations;*/
}
