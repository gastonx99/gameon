package se.dandel.gameon.adapter.in.rest;

import static java.util.stream.Collectors.toList;

import java.util.Collection;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import se.dandel.gameon.domain.model.Tournament;
import se.dandel.gameon.domain.repository.TournamentRepository;

@Path("/tournament")
public class TournamentAPI {

    @Inject
    private TournamentRepository tournamentRepository;

    @Inject
    private TournamentMapper mapper;

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        Collection<Tournament> tournaments = tournamentRepository.findAllTournaments();
        return Response.ok(tournaments.stream().map(t -> mapper.map(t)).collect(toList())).build();
    }

}
