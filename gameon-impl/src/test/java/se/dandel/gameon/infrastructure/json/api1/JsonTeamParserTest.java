package se.dandel.gameon.infrastructure.json.api1;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.dandel.gameon.infrastructure.json.api1.data.LeagueDTO;
import se.dandel.gameon.infrastructure.json.api1.data.MatchDTO;
import se.dandel.gameon.infrastructure.json.api1.data.SeasonDTO;
import se.dandel.gameon.infrastructure.json.api1.data.TeamDTO;

import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class JsonTeamParserTest {

    final Logger LOGGER = LoggerFactory.getLogger(getClass());

    JsonTeamParser parser = new JsonTeamParser();

    @Test
    void parseTeams() throws Exception {
        // Given
        String json = IOUtils.toString(getClass().getResource("/json/api1/teams.json"), "UTF-8");

        // When
        Collection<TeamDTO> actuals = parser.parseTeams(json);
        LOGGER.debug("Actuals: {}", actuals);


        // Then
        assertThat(actuals.size(), is(equalTo(3)));
    }

    @Test
    void parseLeagues() throws Exception {
        // Given
        String json = IOUtils.toString(getClass().getResource("/json/api1/leagues.json"), "UTF-8");

        // When
        Collection<LeagueDTO> actuals = parser.parseLeagues(json);
        LOGGER.debug("Actuals: {}", actuals);


        // Then
        assertThat(actuals.size(), is(equalTo(6)));
    }

    @Test
    void parseSeasons() throws Exception {
        // Given
        String json = IOUtils.toString(getClass().getResource("/json/api1/seasons.json"), "UTF-8");

        // When
        Collection<SeasonDTO> actuals = parser.parseSeasons(json);
        LOGGER.debug("Actuals: {}", actuals);


        // Then
        assertThat(actuals.size(), is(equalTo(3)));
    }

    @Test
    void parseMatches() throws Exception {
        // Given
        String json = IOUtils.toString(getClass().getResource("/json/api1/matches.json"), "UTF-8");

        // When
        List<MatchDTO> actuals = parser.parseMatches(json);
        LOGGER.debug("Actuals: {}", actuals);


        // Then
        assertThat(actuals.size(), is(equalTo(3)));
        assertThat(actuals.get(0).getMatchStart(), is(equalTo("2020-09-19 15:30:00")));
        assertThat(actuals.get(1).getMatchStart(), is(equalTo("2020-09-20 15:30:00")));
        assertThat(actuals.get(2).getMatchStart(), is(equalTo("2020-09-21 15:30:00")));
    }
}