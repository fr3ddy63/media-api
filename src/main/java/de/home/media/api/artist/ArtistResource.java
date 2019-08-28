package de.home.media.api.artist;

import de.home.media.api.common.Parameter;
import de.home.media.api.common.UriBuilder;
import de.home.media.api.medium.ArtistMediaResource;
import de.home.media.api.medium.MediumResource;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@RequestScoped
@Path("/artists")
@Produces(MediaType.APPLICATION_JSON)
public class ArtistResource {

    @Inject
    private MediumResource mediumResource;

    @Inject
    private ArtistMediaResource artistMediaResource;

    @Inject
    private ArtistService service;

    @GET
    public Response getArtists(@Context UriInfo info, @Valid @BeanParam Parameter param) {
        List<Artist> artists = this.service.find(param);
        Long countArtists = this.service.count();
        JsonArrayBuilder jab = Json.createArrayBuilder();
        artists.forEach(artist -> jab.add(Json.createObjectBuilder()
                .add("id", artist.getId())
                .add("name", artist.getName())
                .add("artist", info.getAbsolutePathBuilder()
                        .path(ArtistResource.class, "getArtist")
                        .build(artist.getId()).toString())
                .add("media", info.getAbsolutePathBuilder()
                        .path(ArtistResource.class, "getArtistMediaSubResource")
                        .build(artist.getId()).toString())
        ));
        List<Link> links = UriBuilder.links(info, param, countArtists);
        return Response.ok(jab.build()).links(links.toArray(new Link[0])).build();
    }

    @GET
    @Path("/{id}")
    public Response getArtist(@Context UriInfo info, @PathParam("id") Integer id) {
        Artist artist = this.service.find(id).orElseThrow(NotFoundException::new);
        JsonObjectBuilder job = Json.createObjectBuilder()
                .add("id", artist.getId())
                .add("name", artist.getName())
                .add("media", info.getAbsolutePathBuilder()
                        .path(ArtistResource.class, "getArtist")
                        .path("media")
                        .build(artist.getId()).toString());
        return Response.ok(job.build()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(@Context UriInfo info, @Valid ArtistPost post) {
        Artist entity = post.toEntity();
        this.service.persist(entity);
        URI location = info.getAbsolutePathBuilder().path(entity.getId().toString()).build();
        return Response.created(location).build();
    }

    @Path("/{id}/media")
    public ArtistMediaResource getArtistMediaSubResource(@PathParam("id") Integer id) {
        this.artistMediaResource.setArtist(this.service.find(id).orElseThrow(NotFoundException::new));
        return this.artistMediaResource;
    }
}
