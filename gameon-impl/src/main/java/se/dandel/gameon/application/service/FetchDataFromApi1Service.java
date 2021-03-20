package se.dandel.gameon.application.service;

import se.dandel.gameon.domain.model.Team;
import se.dandel.gameon.domain.port.Api1Port;
import se.dandel.gameon.domain.repository.TeamRepository;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Optional;

public class FetchDataFromApi1Service {

    @Inject
    private Api1Port api1Port;

    @Inject
    private TeamRepository teamRepository;

    public void fetchTeams() {
        Collection<Team> teams = api1Port.fetchTeams();
        teams.stream().forEach(team -> createOrUpdate(team));
    }

    private void createOrUpdate(Team team) {
        Optional<Team> persistedTeam = teamRepository.find(team.getKey());
        if (persistedTeam.isPresent()) {
            apply(team, persistedTeam.get());
        } else {
            teamRepository.persist(team);
        }
    }

    private void apply(Team source, Team target) {
        target.setName(source.getName());
        target.setCountryCode(source.getCountryCode());
        target.setLogo(source.getLogo());
    }


}
