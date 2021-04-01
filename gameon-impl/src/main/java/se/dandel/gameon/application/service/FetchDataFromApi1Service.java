package se.dandel.gameon.application.service;

import se.dandel.gameon.domain.model.Team;
import se.dandel.gameon.domain.model.Tournament;
import se.dandel.gameon.domain.port.Api1Port;
import se.dandel.gameon.domain.repository.TeamRepository;
import se.dandel.gameon.domain.repository.TournamentRepository;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Optional;

public class FetchDataFromApi1Service {

    @Inject
    private Api1Port api1Port;

    @Inject
    private TeamRepository teamRepository;

    @Inject
    private TournamentRepository tournamentRepository;

    public void fetchAndSaveTeams() {
        Collection<Team> teams = api1Port.fetchTeams();
        teams.forEach(team -> createOrUpdate(team));
    }

    public void fetchAndSaveLeagues() {
        Collection<Tournament> tournaments = api1Port.fetchLeagues();
        tournaments.forEach(tournament -> createOrUpdate(tournament));
    }

    private void createOrUpdate(Team team) {
        Optional<Team> persisted = teamRepository.find(team);
        if (persisted.isPresent()) {
            apply(team, persisted.get());
        } else {
            teamRepository.persist(team);
        }
    }

    private void apply(Team source, Team target) {
        target.setName(source.getName());
        target.setCountryCode(source.getCountryCode());
        target.setLogo(source.getLogo());
    }

    private void createOrUpdate(Tournament tournament) {
        Optional<Tournament> persisted = tournamentRepository.find(tournament);
        if (persisted.isPresent()) {
            apply(tournament, persisted.get());
        } else {
            teamRepository.persist(tournament);
        }
    }

    private void apply(Tournament source, Tournament target) {
        target.setName(source.getName());
        target.setCountryCode(source.getCountryCode());
    }
}
