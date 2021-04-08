package se.dandel.gameon.adapter.in.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.dandel.gameon.domain.repository.TournamentRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
