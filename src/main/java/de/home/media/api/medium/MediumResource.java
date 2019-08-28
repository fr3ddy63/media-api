package de.home.media.api.medium;

import de.home.media.api.artist.Artist;
import de.home.media.api.artist.ArtistResource;
import de.home.media.api.artist.ArtistService;
import de.home.media.api.common.Parameter;
import de.home.media.api.common.UriBuilder;
import de.home.media.api.location.Location;
import de.home.media.api.location.LocationResource;
import de.home.media.api.location.LocationService;
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
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class MediumResource {

    @Inject
    private MediumService service;

    @Inject
    private ArtistService artistService;

    @Inject
    private LocationService locationService;

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
    @Path("/media")
    public Response getMedia(@Context UriInfo info, @Valid @BeanParam Parameter param) {
        List<Medium> media = this.service.find(param);
        Long countMedia = this.service.count();
        JsonArrayBuilder jab = Json.createArrayBuilder();
        media.forEach(medium -> jab.add(Json.createObjectBuilder()
                .add("id", medium.getId())
                .add("title", medium.getTitle())
                .add("description", medium.getDescription())
                .add("artist", medium.getArtist().getName())
                .add("artist_url", info.getBaseUriBuilder()
                        .path(ArtistResource.class)
                        .path(ArtistResource.class, "getArtist")
                        .build(medium.getArtist().getId()).toString())
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

    @GET
    @Path("/media/{id:[1-9][0-9]*}")
    public Response getMedium(@Context UriInfo info, @PathParam("id") Integer id) {
        Medium medium = this.service.find(id).orElseThrow(NotFoundException::new);
        JsonArrayBuilder jab = Json.createArrayBuilder();
        jab.add(Json.createObjectBuilder()
                .add("id", medium.getId())
                .add("title", medium.getTitle())
                .add("description", medium.getDescription())
                .add("artist", medium.getArtist().getName())
                .add("artist_url", info.getBaseUriBuilder()
                        .path(ArtistResource.class)
                        .path(ArtistResource.class, "getArtist")
                        .build(medium.getArtist().getId()).toString())
                .add("location", medium.getLocation().getLocation().getName())
                .add("location_url", info.getBaseUriBuilder()
                        .path(LocationResource.class)
                        .path(LocationResource.class, "getLocation")
                        .build(medium.getLocation().getLocation().getName()).toString())
                .add("index", medium.getLocation().getIndex())
        );
        return Response.ok(jab.build()).build();
    }

    @POST
    @Path("/mediax")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(@Context UriInfo info, @Valid MediumXPost post) {
        Artist postArtist = this.artistService.find(post.getArtist()).orElse(new Artist(post.getArtist()));
        Location postLocation = this.locationService.find(post.getLocation()).orElse(new Location(post.getLocation()));
        MediumLocation postMediumLocation = this.mediumLocationService
                .find(post.getIndex(), postLocation)
                .orElse(new MediumLocation(0, postLocation));
        Medium entity = post.toEntity(postArtist, postMediumLocation);
        this.service.persist(entity);
        URI location = info.getAbsolutePathBuilder().path(entity.getId().toString()).build();
        return Response.created(location).build();
    }

    /*@POST
    @Path("/media")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(@Context UriInfo info, @Valid MediumPost post) {
        if (this.artist == null) throw new NotFoundException();
        if (this.location == null) throw new NotFoundException();

        Optional<MediumLocation> postMediumLocation = this.mediumLocationService.find(
                post.getIndex(), this.location);

        if (postMediumLocation.isPresent()) {
            return Response.status(400, "The index in that location is already occupied.").build();
        }

        Medium entity = new Medium();
        entity.setTitle(post.getTitle());
        entity.setDescription(post.getDescription());
        entity.setArtist(this.artist);
        entity.setLocation(new MediumLocation(post.getIndex(), this.location));

        this.service.persist(entity);
        URI location = info.getAbsolutePathBuilder().path(entity.getId().toString()).build();
        return Response.created(location).build();
    }*/
}
