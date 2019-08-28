package de.home.media.api.medium;

import de.home.media.api.artist.Artist;
import de.home.media.api.mediumlocation.MediumLocation;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Medium.class)
public class Medium_ {
    public static volatile SingularAttribute<Medium, Integer> id;
    public static volatile SingularAttribute<Medium, String> title;
    public static volatile SingularAttribute<Medium, String> description;
    public static volatile SingularAttribute<Medium, Artist> artist;
    public static volatile SingularAttribute<Medium, MediumLocation> location;
}
