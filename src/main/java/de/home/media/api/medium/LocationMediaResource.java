package de.home.media.api.medium;

import de.home.media.api.artist.ArtistResource;
import de.home.media.api.common.Parameter;
import de.home.media.api.common.UriBuilder;
import de.home.media.api.location.Location;
import de.home.media.api.location.LocationResource;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
public class LocationMediaResource {

    @Inject
    private MediumService service;

    private Location location;
    public void setLocation(Location location) {
        this.location = location;
    }

    @GET
    public Response get(@Context UriInfo info, @Valid @BeanParam Parameter param) {
        List<Medium> media = this.service.find(this.location, param);
        Long countMedia = this.service.count(location);

        JsonArrayBuilder jab = Json.createArrayBuilder();
        media.forEach(medium -> jab.add(Json.createObjectBuilder()
            .add("id", medium.getId())
            .add("title", medium.getTitle())
            .add("description", medium.getDescription())
            .add("artist", medium.getArtist().toString())
            .add("artist_url", info.getBaseUriBuilder()
                    .path(ArtistResource.class)
                    .path(ArtistResource.class, "getArtist")
                    .build(medium.getArtist().getId()).toString())
            .add("location", medium.getLocation().toString())
            .add("location_url", info.getBaseUriBuilder()
                    .path(LocationResource.class)
                    .path(LocationResource.class, "getLocation")
                    .build(medium.getLocation().getLocation().getName()).toString())
        ));
        List<Link> links = UriBuilder.links(info, param, countMedia);
        return Response.ok(jab.build())
                .links(links.toArray(new Link[0]))
                .build();
    }
}
