package de.home.media.api.group;

import de.home.media.api.user.User;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Group.class)
public class Group_ {
    public static volatile SingularAttribute<Group, Integer> id;
    public static volatile SingularAttribute<Group, String> name;
}
