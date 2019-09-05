package de.home.media.api.security;

import javax.annotation.Priority;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
@Secured
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationRequestFilter implements ContainerRequestFilter {

    @Inject
    private TokenProvider tokenProvider;

    @Inject
    @Caller
    private Event<String> event;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        String header = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (header == null || !header.startsWith(Constants.BEARER)) {
            // throw new NotAuthorizedException("authentication error: missing authorization header.");
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }

        String token = header.substring(Constants.BEARER.length());

        if (!this.tokenProvider.validateToken(token)) {
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }

        JWTCredential credential = this.tokenProvider.getCredential(token);

        containerRequestContext.setSecurityContext(new JWTSecurityContext(new JWTUserPrincipal(
                credential.getPrincipal(), credential.getAuthorities())));
    }
}
