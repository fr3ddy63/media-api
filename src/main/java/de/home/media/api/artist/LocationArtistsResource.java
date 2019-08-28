package de.home.media.api.artist;

import de.home.media.api.common.Parameter;
import de.home.media.api.common.UriBuilder;
import de.home.media.api.location.Location;
import de.home.media.api.medium.LocationArtistMediaResource;
import de.home.media.api.medium.Medium;
import de.home.media.api.medium.MediumService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
public class LocationArtistsResource {

    @Inject
    private LocationArtistMediaResource locationArtistMediaResource;

    @Inject
    private ArtistService artistService;

    @Inject
    private MediumService mediumService;

    private Location location;
    public void setLocation(Location location) {
        this.location = location;
    }

    @GET
    public Response get(@Context UriInfo info, @Valid @BeanParam Parameter param) {
        List<Medium> media = this.mediumService.find(this.location, param);
        Set<String> artistNames = new HashSet<>();
        media.forEach(medium -> artistNames.add(medium.getArtist().getName()));
        JsonArrayBuilder jab = Json.createArrayBuilder();
        artistNames.forEach(jab::add);
        List<Link> links = UriBuilder.links(info, param, (long)artistNames.size());
        return Response.ok(jab.build()).links(links.toArray(new Link[0])).build();
    }

    @Path("/{id}/media")
    public LocationArtistMediaResource get(@PathParam("id") Integer id) {
        this.locationArtistMediaResource.setArtist(this.artistService.find(id).orElseThrow(NotFoundException::new));
        this.locationArtistMediaResource.setLocation(this.location);
        return this.locationArtistMediaResource;
    }
}
