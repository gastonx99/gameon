package se.dandel.gameon.adapter.in.rest;

import se.dandel.gameon.domain.model.Tournament;
import se.dandel.gameon.domain.repository.TournamentRepository;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
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
