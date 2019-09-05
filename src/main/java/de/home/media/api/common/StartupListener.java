package de.home.media.api.common;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.servlet.ServletContext;

@ApplicationScoped
public class StartupListener {

    public void init(@Observes @Initialized(ApplicationScoped.class) ServletContext context) {
        // Perform action during application's startup
    }

    public void destroy(@Observes @Destroyed(ApplicationScoped.class) ServletContext context) {
        // Perform action during application's shutdown
    }
}
