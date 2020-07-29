package se.dandel.gameon.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import se.dandel.gameon.infrastructure.jpa.JpaTournamentRepository;

@Path("/monitor")
public class MonitorAPI {

    @Inject
    private JpaTournamentRepository tournamentRepository;

    @Inject
    private TournamentMapper mapper;

    @GET
    @Path("/ping")
    @Produces(MediaType.TEXT_PLAIN)
    public Response findAll() {
        return Response.ok("I'm alive").build();
    }

}
