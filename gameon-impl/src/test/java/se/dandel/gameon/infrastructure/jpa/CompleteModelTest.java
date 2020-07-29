package se.dandel.gameon.infrastructure.jpa;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.jboss.weld.junit5.EnableWeld;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.dandel.gameon.datamodel.test.jpa.JpaTestManager;
import se.dandel.gameon.datamodel.test.jpa.JpaTestManagerExtension;
import se.dandel.gameon.domain.model.Match;
import se.dandel.gameon.domain.model.Season;
import se.dandel.gameon.domain.model.Team;
import se.dandel.gameon.domain.model.TestTournamentFactory;
import se.dandel.gameon.domain.model.Tournament;
import se.dandel.gameon.infrastructure.json.JsonMatchParser;

@ExtendWith(JpaTestManagerExtension.class)
@EnableWeld
@Disabled
class CompleteModelTest {

    Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Inject
    JpaTeamRepository teamRepository;

    @Inject
    JpaTournamentRepository tournamentRepository;

    JsonMatchParser matchParser = new JsonMatchParser();

    @Test
    void persist(JpaTestManager jpaTestManager) throws Exception {
        // Given
        Tournament expected = TestTournamentFactory.createTournament();
        String json = IOUtils.toString(getClass().getResource("/json/WorldCup2018.json"), "UTF-8");
        matchParser.parseMatches(json, expected.getSeasons().iterator().next());

        Comparator<Team> comparator = (o1, o2) -> o1.getName().compareTo(o2.getName());
        Collection<Team> teams = new TreeSet<>(comparator);
        for (Season season : expected.getSeasons()) {
            for (Match match : season.getMatches()) {
                teams.add(match.getHomeTeam());
                teams.add(match.getAwayTeam());
            }
        }

        // When
        teams.stream().forEach(t -> teamRepository.persist(t));
        tournamentRepository.persist(expected);

        // Then
        jpaTestManager.getEntityManager().flush();
        jpaTestManager.reset();
        Tournament actual = tournamentRepository.getTournament(expected.getPk());
        assertThat(actual.getName(), is(equalTo(expected.getName())));
        Season season = actual.getSeasons().iterator().next();
        assertThat(season.getMatches().size(), is(equalTo(48)));
    }

    private Map<String, String> readPersistenceProperties() {

        Map<String, String> props = new HashMap<>();
        //        if (persistenceContext != null && persistenceContext.properties() != null) {
        //            Arrays.stream(persistenceContext.properties()).forEach(p -> props.put(p.name(), p.value()));
        //        }

        props.putIfAbsent("javax.persistence.jdbc.url", "jdbc:hsqldb:file:C:/WS/database/hsqldb/gameon.db");
        props.putIfAbsent("javax.persistence.jdbc.user", "gameon");
        props.putIfAbsent("javax.persistence.jdbc.password", "gameon");
        return props;
    }

}