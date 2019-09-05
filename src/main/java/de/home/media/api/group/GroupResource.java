package de.home.media.api.group;

import de.home.media.api.annotations.Name;
import de.home.media.api.common.Parameter;
import de.home.media.api.common.UriBuilder;
import de.home.media.api.security.Secured;
import de.home.media.api.user.User;
import de.home.media.api.user.UserPost;

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
@Path("/group")
@Produces(MediaType.APPLICATION_JSON)
public class GroupResource {

    @Inject
    private GroupService service;

    @GET
    public Response getGroups(@Context UriInfo info, @Valid @BeanParam Parameter param) {
        List<Group> groups = this.service.find(param);
        Long countGroups = this.service.count();

        JsonArrayBuilder jab = Json.createArrayBuilder();
        groups.forEach(group -> jab.add(Json.createObjectBuilder()
                .add("id", group.getId())
                .add("name", group.getName())
        ));

        List<Link> links = UriBuilder.links(info, param, countGroups);
        return Response.ok(jab.build()).links(links.toArray(new Link[0])).build();
    }

    @GET
    @Path("/{name}")
    public Response getGroup(@Context UriInfo info, @PathParam("name") @Name String name) {
        Group group = this.service.find(name).orElseThrow(NotFoundException::new);
        JsonObjectBuilder job = Json.createObjectBuilder()
                .add("id", group.getId())
                .add("name", group.getName());
        return Response.ok(job.build()).build();
    }

    @POST
    @Secured("ADMIN")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(@Context UriInfo info, @Valid GroupPost post) {
        Group group = post.toEntity();
        this.service.persist(group);
        URI location = info.getAbsolutePathBuilder().path(group.getName()).build();
        return Response.created(location).build();
    }
}
