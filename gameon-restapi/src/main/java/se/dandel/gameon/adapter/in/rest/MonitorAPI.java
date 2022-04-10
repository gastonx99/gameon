package se.dandel.gameon.adapter.in.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.dandel.gameon.domain.repository.TournamentRepository;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/monitor")
public class MonitorAPI {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Inject
    private TournamentRepository tournamentRepository;

    @Inject
    private TournamentModelMapper mapper;

    @GET
    @Path("/ping")
    @Produces(MediaType.TEXT_PLAIN)
    public Response findAll() {
        LOGGER.info("Ping");
        return Response.ok("I'm alive").build();
    }

}
