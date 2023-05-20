package se.dandel.gameon.domain.model;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class TestTournamentFactoryTest {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Test
    void createTournamentPremierLeague20202021() {
        // Given

        // When
        Tournament actual = TestTournamentFactory.createTournamentPremierLeague20202021();

        // Then
        Season actualSeason = actual.getSeasons().iterator().next();
        List<Match> matches = new ArrayList(actualSeason.getMatches());
        matches.sort(Comparator.comparing(Match::getMatchStart));
        matches.forEach(m -> LOGGER.atDebug().log("Match: {}", m));
    }

    @Test
    void createTournamentWorldCup2018() {
        // Given

        // When
        Tournament actual = TestTournamentFactory.createCupWorldCup2018();

        // Then
        Season actualSeason = actual.getSeasons().iterator().next();
        List<Match> matches = new ArrayList(actualSeason.getMatches());
        matches.sort(Comparator.comparing(Match::getMatchStart));
        matches.forEach(m -> LOGGER.atDebug().log("Match: {}", m));
    }
}