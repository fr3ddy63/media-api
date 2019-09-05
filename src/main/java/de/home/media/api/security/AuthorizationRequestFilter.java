package de.home.media.api.security;

import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

@Provider
@Secured
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationRequestFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        String[] roles = this.resourceInfo.getResourceMethod().getAnnotation(Secured.class).value();

        if (roles.length == 0) return;

        for (String role : roles) {
            if (containerRequestContext.getSecurityContext().isUserInRole(role))
                return;
        }

        throw new NotAuthorizedException("You don't have permissions to perform this action.");
    }
}
