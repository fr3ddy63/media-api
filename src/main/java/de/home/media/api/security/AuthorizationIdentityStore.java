package de.home.media.api.security;

import de.home.media.api.group.Group;
import de.home.media.api.user.User;
import de.home.media.api.user.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import javax.ws.rs.NotFoundException;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Collections.singleton;

@RequestScoped
public class AuthorizationIdentityStore implements IdentityStore {

    @Inject
    private UserService service;

    @Override
    public int priority() {
        return 90;
    }

    @Override
    public Set<String> getCallerGroups(CredentialValidationResult validationResult) {

        User user = this.service.find(validationResult.getCallerPrincipal().getName())
                .orElseThrow(NotFoundException::new);

        return user.getGroups().stream().map(Group::getName).collect(Collectors.toSet());
    }

    @Override
    public Set<ValidationType> validationTypes() {
        return singleton(ValidationType.PROVIDE_GROUPS);
    }
}
