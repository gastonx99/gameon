package se.dandel.gameon.adapter.in.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import se.dandel.gameon.domain.model.Tournament;
import se.dandel.gameon.domain.repository.TournamentRepository;

import java.util.Collection;

import static java.util.stream.Collectors.toList;

@RestController("/tournament")
public class TournamentAPI {

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private TournamentModelMapper mapper;

    @GetMapping("/all")
    public Collection<TournamentModel> findAll() {
        Collection<Tournament> tournaments = tournamentRepository.findAll();
        return tournaments.stream().map(t -> mapper.toModel(t)).collect(toList());
    }

}
