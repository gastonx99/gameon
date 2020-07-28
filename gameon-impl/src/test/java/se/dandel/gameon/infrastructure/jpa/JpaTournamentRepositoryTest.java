package se.dandel.gameon.infrastructure.jpa;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.jboss.weld.junit5.EnableWeld;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.dandel.gameon.datamodel.test.jpa.JpaTestManager;
import se.dandel.gameon.datamodel.test.jpa.JpaTestManagerExtension;
import se.dandel.gameon.domain.model.Match;
import se.dandel.gameon.domain.model.Team;
import se.dandel.gameon.domain.model.TestTournamentFactory;
import se.dandel.gameon.domain.model.Tournament;

@ExtendWith(JpaTestManagerExtension.class)
@EnableWeld
class JpaTournamentRepositoryTest {

    Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Inject
    JpaTournamentRepository repository;

    @Test
    void findAll(JpaTestManager jpaTestManager) {
        // Given
        Tournament expected = TestTournamentFactory.createTournament();
        List<Team> teams = expected.getSeasons().stream().flatMap(t -> t.getTeams().stream()).collect(toList());
        teams.forEach(team -> repository.persist(team));
        repository.persist(expected);

        // When
        jpaTestManager.reset();
        Collection<Match> actualMatches = repository.findAllMatches();

        // Then
        assertThat(actualMatches.size(), is(equalTo(1)));
        Match actual = actualMatches.iterator().next();
        assertThat(actual.getPk(), is(greaterThan(0L)));

    }

}