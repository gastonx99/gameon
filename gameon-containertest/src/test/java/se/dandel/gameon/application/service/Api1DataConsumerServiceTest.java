package se.dandel.gameon.application.service;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.dandel.gameon.datamodel.test.jpa.ContainerTest;
import se.dandel.gameon.datamodel.test.jpa.PersistenceTestManager;
import se.dandel.gameon.domain.model.Team;
import se.dandel.gameon.infrastructure.json.api1.data.TeamDTO;

import javax.inject.Inject;
import javax.persistence.TypedQuery;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@ContainerTest
class Api1DataConsumerServiceTest {
    final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Inject
    Api1DataConsumerService service;

    @Inject
    PersistenceTestManager persistManager;

    @Test
    void newTeam() {
        // Given
        TeamDTO expected = createTeamDTO();

        // When
        service.handle(expected);

        // Then
        TypedQuery<Team> query = persistManager.em().createQuery("select t from Team t where t.key = :key", Team.class);
        query.setParameter("key", expected.getShortCode());
        Team actual = query.getSingleResult();
        assertThat(actual.getName(), is(equalTo(expected.getName())));
    }

    private TeamDTO createTeamDTO() {
        TeamDTO dto = new TeamDTO();
        dto.setShortCode("MUN");
        dto.setName("Manchester United");
        return dto;
    }
}