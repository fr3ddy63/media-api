package de.home.media.api.medium;

import de.home.media.api.artist.Artist;
import de.home.media.api.artist.ArtistResource;
import de.home.media.api.common.Parameter;
import de.home.media.api.common.UriBuilder;
import de.home.media.api.location.LocationResource;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.util.List;

@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
public class ArtistMediaResource {

    @Inject
    private MediumService service;

    private Artist artist;
    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    @GET
    public Response get(@Context UriInfo info, @Valid @BeanParam Parameter param) {
        List<Medium> media = this.service.find(this.artist, param);
        Long countMedia = this.service.count(artist);

        JsonArrayBuilder jab = Json.createArrayBuilder();
        media.forEach(medium -> jab.add(Json.createObjectBuilder()
                .add("id", artist.getId())
                .add("title", artist.getName())
                .add("artist", info.getBaseUriBuilder()
                        .path(ArtistResource.class)
                        .path(ArtistResource.class, "getArtist")
                        .build(artist.getId()).toString())
                .add("location", info.getBaseUriBuilder()
                        .path(LocationResource.class)
                        .path(LocationResource.class, "getLocation")
                        .build(medium.getLocation().getId()).toString())
        ));
        List<Link> links = UriBuilder.links(info, param, countMedia);
        return Response.ok(jab.build())
                .links(links.toArray(new Link[0]))
                .build();
    }
}
