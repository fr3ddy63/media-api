package de.home.media.api.security;

import de.home.media.api.user.User;
import de.home.media.api.user.UserService;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

@RequestScoped
public class CallerProducer {

    @Inject
    private UserService service;

    @Produces
    @Caller
    @RequestScoped
    private User caller;

    public void handle(@Observes @Caller String name) {
        this.caller = this.service.find(name).orElseThrow(NotFoundException::new);
    }
}
