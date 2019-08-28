package de.home.media.api.medium;

import de.home.media.api.artist.Artist;
import de.home.media.api.artist.ArtistResource;
import de.home.media.api.common.Parameter;
import de.home.media.api.common.UriBuilder;
import de.home.media.api.location.Location;
import de.home.media.api.location.LocationResource;
import de.home.media.api.mediumlocation.MediumLocation;
import de.home.media.api.mediumlocation.MediumLocationService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
public class LocationArtistMediaResource {

    @Inject
    private MediumService service;

    @Inject
    private MediumLocationService mediumLocationService;

    private Artist artist;
    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    private Location location;
    public void setLocation(Location location) {
        this.location = location;
    }

    @GET
    public Response get(@Context UriInfo info, @Valid @BeanParam Parameter param) {
        List<Medium> media = service.find(this.location, this.artist, param);
        Long countMedia = this.service.count(this.location, this.artist);

        JsonArrayBuilder jab = Json.createArrayBuilder();
        media.forEach(medium -> jab.add(Json.createObjectBuilder()
                .add("id", medium.getId())
                .add("artist", medium.getArtist().getName())
                .add("artist_url", info.getBaseUriBuilder()
                        .path(ArtistResource.class)
                        .path(ArtistResource.class, "getArtist")
                        .build(medium.getArtist().getId()).toString())
                .add("title", medium.getTitle())
                .add("description", medium.getDescription())
                .add("location", medium.getLocation().getLocation().getName())
                .add("location_url", info.getBaseUriBuilder()
                        .path(LocationResource.class)
                        .path(LocationResource.class, "getLocation")
                        .build(medium.getLocation().getLocation().getName()).toString())
                .add("index", medium.getLocation().getIndex())
        ));

        List<Link> links = UriBuilder.links(info, param, countMedia);
        return Response.ok(jab.build()).links(links.toArray(new Link[0])).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(@Context UriInfo info, @Valid MediumPost post) {
        MediumLocation mediumLocation = new MediumLocation(post.getIndex(), this.location);
        this.mediumLocationService.persist(mediumLocation);

        Medium entity = new Medium();
        entity.setTitle(post.getTitle());
        entity.setDescription(post.getDescription());
        entity.setArtist(this.artist);
        entity.setLocation(mediumLocation);
        this.service.persist(entity);
        URI location = info.getBaseUriBuilder()
                .path(MediumResource.class)
                .path(MediumResource.class, "getMedium")
                .build(entity.getId());
        return Response.created(location).build();
    }
}
