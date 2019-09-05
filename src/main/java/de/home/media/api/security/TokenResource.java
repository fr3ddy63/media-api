package de.home.media.api.security;

import de.home.media.api.group.Group;
import de.home.media.api.user.User;
import de.home.media.api.user.UserService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.stream.Collectors;

@RequestScoped
@Path("/token")
public class TokenResource {

    @Inject
    private UserService service;

    @Inject
    private TokenProvider tokenProvider;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(@Valid CredentialPost credential) {
        User user = this.service.find(credential.getName()).orElseThrow(NotFoundException::new);
        if (!user.getPassword().equals(credential.getPassword()))
            throw new NotAuthorizedException("error: user password invalid.");
        String token = this.tokenProvider.createToken(
                user.getName(),
                user.getGroups().stream().map(Group::getName).collect(Collectors.toSet()));

        return Response.ok().header(HttpHeaders.AUTHORIZATION, Constants.BEARER.concat(token)).build();
    }
}
