package de.home.media.api.group;

import de.home.media.api.common.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "GROUP_ENTITY")
public class Group extends BaseEntity {

    @Column(length = 50, unique = true, nullable = false)
    private String name;

    public Group() {
    }

    public Group(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
