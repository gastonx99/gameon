package se.dandel.gameon.adapter.in.rest;

import se.dandel.gameon.domain.model.Tournament;
import se.dandel.gameon.domain.repository.TournamentRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

import static java.util.stream.Collectors.toList;

@Path("/tournament")
public class TournamentAPI {

    @Inject
    private TournamentRepository tournamentRepository;

    @Inject
    private TournamentModelMapper mapper;

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        Collection<Tournament> tournaments = tournamentRepository.findAllTournaments();
        return Response.ok(tournaments.stream().map(t -> mapper.toModel(t)).collect(toList())).build();
    }

}
