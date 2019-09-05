package de.home.media.api.user;

import de.home.media.api.common.Parameter;
import de.home.media.api.common.UriBuilder;
import de.home.media.api.annotations.Name;
import de.home.media.api.group.Group;
import de.home.media.api.group.GroupService;
import de.home.media.api.security.Secured;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@RequestScoped
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    private UserService service;

    @Inject
    private GroupService groupService;

    @GET
    public Response getUsers(@Context UriInfo info, @Valid @BeanParam Parameter param) {
        List<User> users = this.service.find(param);
        Long countUsers = this.service.count();

        JsonArrayBuilder jab = Json.createArrayBuilder();
        users.forEach(user -> jab.add(Json.createObjectBuilder()
                .add("id", user.getId())
                .add("name", user.getName())
                .add("email", user.getEmail())
        ));

        List<Link> links = UriBuilder.links(info, param, countUsers);
        return Response.ok(jab.build()).links(links.toArray(new Link[0])).build();
    }

    @GET
    @Path("/{name}")
    public Response getUser(@Context UriInfo info, @PathParam("name") @Name String name) {
        User user = this.service.find(name).orElseThrow(NotFoundException::new);
        JsonObjectBuilder job = Json.createObjectBuilder()
                .add("id", user.getId())
                .add("name", user.getName())
                .add("email", user.getEmail());
        return Response.ok(job.build()).build();
    }

    @POST
    @Secured("ADMIN")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(@Context UriInfo info, @Valid UserPost post) {

        List<Group> groups = groupService.find();

        User user = null;
        try {
            user = post.toEntity(groups);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        this.service.persist(user);
        URI location = info.getAbsolutePathBuilder().path(user.getName()).build();
        return Response.created(location).build();
    }
}
