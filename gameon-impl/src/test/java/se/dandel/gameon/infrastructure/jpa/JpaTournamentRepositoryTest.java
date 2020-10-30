package se.dandel.gameon.infrastructure.jpa;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.dandel.gameon.datamodel.test.jpa.IntegrationTest;
import se.dandel.gameon.datamodel.test.jpa.PersistenceTestManager;
import se.dandel.gameon.domain.model.Match;
import se.dandel.gameon.domain.model.Team;
import se.dandel.gameon.domain.model.TestTournamentFactory;
import se.dandel.gameon.domain.model.Tournament;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@IntegrationTest
class JpaTournamentRepositoryTest {

    Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Inject
    JpaTournamentRepository repository;

    @Inject
    PersistenceTestManager persistManager;

    @Test
    void findAll() {
        // Given
        Tournament expected = TestTournamentFactory.createTournament();
        List<Team> teams = expected.getSeasons().stream().flatMap(t -> t.getTeams().stream()).collect(toList());
        teams.forEach(team -> repository.persist(team));
        repository.persist(expected);

        // When
        persistManager.reset();
        Collection<Match> actualMatches = repository.findAllMatches();

        // Then
        assertThat(actualMatches.size(), is(equalTo(1)));
        Match actual = actualMatches.iterator().next();
        assertThat(actual.getPk(), is(greaterThan(0L)));

    }

}