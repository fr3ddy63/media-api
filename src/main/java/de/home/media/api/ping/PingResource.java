package de.home.media.api.ping;

import de.home.media.api.security.Secured;

import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Instant;

@RequestScoped
@Path("/ping")
@Produces(MediaType.APPLICATION_JSON)
public class PingResource {

    @GET
    @Secured
    public Response get() {
        JsonObjectBuilder job = Json.createObjectBuilder()
                .add("pong", Instant.now().toString());
        return Response.ok(job.build()).build();
    }
}
