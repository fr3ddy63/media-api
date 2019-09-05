package de.home.media.api.group;

import de.home.media.api.annotations.Name;

import javax.validation.constraints.NotNull;

public class GroupPost {

    @Name
    @NotNull
    private String name;

    public GroupPost() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public Group toEntity() {
        Group group = new Group();
        group.setName(this.name);
        return group;
    }
}
