package se.dandel.gameon.adapter.in.rest;

import se.dandel.gameon.domain.model.bet.BettingGameUser;
import se.dandel.gameon.domain.repository.BettingGameRepository;
import se.dandel.gameon.adapter.in.rest.BettingGameUserModelMapper;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/betting")
public class BettingAPI {

    @Inject
    private BettingGameRepository bettingGameRepository;

    @Inject
    private BettingGameUserModelMapper mapper;

    @GET
    @Path("/game/user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") long id) {
        BettingGameUser bettingGame = bettingGameRepository.getBettingGameUser(id);
        return Response.ok(mapper.toModel(bettingGame)).build();
    }

}
