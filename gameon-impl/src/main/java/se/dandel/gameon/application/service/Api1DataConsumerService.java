package se.dandel.gameon.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.dandel.gameon.domain.model.Team;
import se.dandel.gameon.infrastructure.jpa.JpaTeamRepository;
import se.dandel.gameon.infrastructure.json.api1.data.TeamDTO;

import javax.inject.Inject;

public class Api1DataConsumerService {
    final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Inject
    private JpaTeamRepository teamRepository;

    public void handle(TeamDTO teamDTO) {
        LOGGER.debug("Handle {}", teamDTO);
        Team team = Team.of(teamDTO.getShortCode(), teamDTO.getName());
        teamRepository.persist(team);
    }
}
