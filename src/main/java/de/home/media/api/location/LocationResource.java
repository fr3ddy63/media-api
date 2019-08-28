package de.home.media.api.location;

import de.home.media.api.artist.LocationArtistsResource;
import de.home.media.api.common.JsonArrayCollector;
import de.home.media.api.common.Parameter;
import de.home.media.api.common.UriBuilder;
import de.home.media.api.medium.LocationMediaResource;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@RequestScoped
@Path("/locations")
@Produces(MediaType.APPLICATION_JSON)
public class LocationResource {

    @Inject
    private LocationArtistsResource locationArtistsResource;

    @Inject
    private LocationMediaResource locationMediaResource;

    @Inject
    private LocationService service;

    @GET
    public Response getLocations(@Context UriInfo info, @Valid @BeanParam Parameter param) {
        Long countLocations = this.service.count();
        List<Link> links = UriBuilder.links(info, param, countLocations);
        JsonArray result = this.service.find(param).stream()
                .map(location -> Json.createObjectBuilder()
                        .add("id", location.getId())
                        .add("name", location.getName())
                        .add("location", info.getAbsolutePathBuilder()
                                .path(LocationResource.class, "getLocation")
                                .build(location.getId()).toString())
                        .add("media", info.getAbsolutePathBuilder()
                                .path(LocationResource.class, "getLocationMediaSubResource")
                                .build(location.getId()).toString())
                        .build())
                .collect(new JsonArrayCollector()).build();
        return Response.ok(result)
                .links(links.toArray(new Link[0]))
                .build();

        /*List<Location> locations = this.service.find(param);
        Long countLocations = this.service.count();
        JsonArrayBuilder jab = Json.createArrayBuilder();
        locations.forEach(location -> jab.add(Json.createObjectBuilder()
            .add("id", location.getId())
            .add("name", location.getName())));
        List<Link> links = UriBuilder.links(info, param, countLocations);
        return Response.ok(jab.build()).build();*/
    }

    @GET
    @Path("/{name:[a-zA-Z0-9]+}")
    public Response getLocation(@Context UriInfo info, @PathParam("name") String name) {
        return Response.ok(
                this.service.find(name)
                        .map(location -> Json.createObjectBuilder()
                                .add("id", location.getId())
                                .add("name", location.getName())
                                .add("media", info.getAbsolutePathBuilder()
                                        .path(LocationResource.class, "getLocationMediaSubResource")
                                        .build(location.getId()).toString())
                                .build())
                        .orElseThrow(NotFoundException::new))
                .build();

        /*Location location = this.service.find(id).orElseThrow(NotFoundException::new);
        JsonObject jo = Json.createObjectBuilder()
                .add("id", location.getId())
                .add("name", location.getName())
                .build();
        return Response.ok(jo).build();*/
    }

    @POST
    public Response postLocation(@Context UriInfo info, @Valid LocationPost post) {
        Location entity = post.toEntity();
        this.service.persist(entity);
        URI location = info.getAbsolutePathBuilder().path(entity.getName()).build();
        return Response.created(location).build();
    }

    /*@POST
    public Response postLocation(@Context UriInfo info, @ValidLocation JsonObject jsonLocation) {
        Location entity = null;
        this.service.persist(entity);
        URI location = info.getAbsolutePathBuilder().path(entity.getId().toString()).build();
        return Response.created(location).build();
    }*/

    @Path("/{name:[a-zA-Z0-9]+}/artists")
    public LocationArtistsResource getArtistSubResource(@PathParam("name") String name) {
        this.locationArtistsResource.setLocation(this.service.find(name).orElseThrow(NotFoundException::new));
        return locationArtistsResource;
    }

    @Path("/{name:[a-zA-Z0-9]+}/media")
    public LocationMediaResource getLocationMediaSubResource(@PathParam("name") String name) {
        this.locationMediaResource.setLocation(this.service.find(name).orElseThrow(NotFoundException::new));
        return this.locationMediaResource;
    }
}
