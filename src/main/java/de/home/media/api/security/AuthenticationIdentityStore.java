package de.home.media.api.security;

import de.home.media.api.artist.ArtistService;
import de.home.media.api.user.User;
import de.home.media.api.user.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import java.util.Collections;
import java.util.Set;

@RequestScoped
public class AuthenticationIdentityStore implements IdentityStore {

    @Inject
    private UserService userService;

    @Override
    public int priority() {
        return 70;
    }

    @Override
    public CredentialValidationResult validate(Credential credential) {
        CredentialValidationResult result;

        if (credential instanceof UsernamePasswordCredential) {
            UsernamePasswordCredential upc = (UsernamePasswordCredential) credential;
            User user = this.userService.find(upc.getCaller()).orElse(null);
            String expectedPassword = user == null ? null : user.getPassword();
            if (expectedPassword != null && expectedPassword.equals(upc.getPasswordAsString())) {
                result = new CredentialValidationResult(upc.getCaller());
            } else {
                result = CredentialValidationResult.INVALID_RESULT;
            }
        } else {
            result = CredentialValidationResult.NOT_VALIDATED_RESULT;
        }

        return result;
    }

    @Override
    public Set<ValidationType> validationTypes() {
        return Collections.singleton(ValidationType.VALIDATE);
    }
}
