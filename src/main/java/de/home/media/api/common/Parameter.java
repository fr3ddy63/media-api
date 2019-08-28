package de.home.media.api.common;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

public class Parameter {

    @QueryParam("page") @DefaultValue("0") @Min(0)
    private Integer page;

    @QueryParam("rpp") @DefaultValue("30") @Min(0) @Max(50)
    private Integer rpp;

    public Parameter() {
    }

    public Integer getPage() {
        return page;
    }

    public Integer getRpp() {
        return rpp;
    }

    public Integer getFirstResult() {
        return this.getPage() * this.getRpp();
    }
}
