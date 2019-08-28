package de.home.media.api.medium;

import javax.validation.constraints.*;

public class MediumPost {

    @NotBlank
    @Size(min = 0, max = 255)
    private String title;

    @Size(min = 0, max = 255)
    private String description;

    @NotNull
    @Min(0)
    private Integer index;

    public MediumPost() {
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getIndex() {
        return index;
    }
}
