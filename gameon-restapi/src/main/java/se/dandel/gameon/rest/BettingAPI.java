package se.dandel.gameon.rest;

import se.dandel.gameon.domain.model.bet.BettingGame;
import se.dandel.gameon.domain.model.bet.BettingGameUser;
import se.dandel.gameon.infrastructure.jpa.JpaBettingGameRepository;
import se.dandel.gameon.interfaces.rest.BettingGameUserModelMapper;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/betting")
public class BettingAPI {

    @Inject
    private JpaBettingGameRepository bettingGameRepository;

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
