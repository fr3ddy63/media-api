package de.home.media.api.artist;

import de.home.media.api.medium.Medium;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Artist.class)
public class Artist_ {
    public static volatile SingularAttribute<Artist, Integer> id;
    public static volatile SingularAttribute<Artist, String> name;
    public static volatile ListAttribute<Artist, Medium> media;
}
