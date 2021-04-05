package se.dandel.gameon.adapter.out.rest.api1;

import org.junit.jupiter.api.Test;
import se.dandel.gameon.domain.model.Match;

import java.time.format.DateTimeFormatter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DtoMapperTest {

    private static final String MATCH_START = "2020-09-19 15:30:00";

    private DtoMapper dtoMapper = new DtoMapperImpl();

    @Test
    void fromMatchDTO() {
        // Given
        MatchDTO expected = createMatchDTO();

        // When
        Match actual = dtoMapper.fromDTO(expected);

        // Then
        assertThat(actual.getMatchStart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), is(equalTo(MATCH_START)));
        assertThat(actual.getHomeTeam().getRemoteKey().getRemoteKey(), is(equalTo(String.valueOf(expected.getHomeTeam().getTeamId()))));
        assertThat(actual.getAwayTeam().getRemoteKey().getRemoteKey(), is(equalTo(String.valueOf(expected.getAwayTeam().getTeamId()))));
        assertThat(actual.getFinalScore().getHome(), is(equalTo(expected.getStats().getHomeScore())));
        assertThat(actual.getFinalScore().getAway(), is(equalTo(expected.getStats().getAwayScore())));
    }

    private MatchDTO createMatchDTO() {
        MatchDTO expected = new MatchDTO();
        expected.setMatchStart(MATCH_START);
        expected.setHomeTeam(createTeamDTO(17, "Hemmalaget"));
        expected.setAwayTeam(createTeamDTO(71, "Bortalaget"));
        expected.setStats(createStatsDTO(3, 4));
        return expected;
    }

    private StatsDTO createStatsDTO(int homeScore, int awayScore) {
        StatsDTO dto = new StatsDTO();
        dto.setHomeScore(homeScore);
        dto.setAwayScore(awayScore);
        return dto;
    }

    private TeamDTO createTeamDTO(int teamId, String name) {
        TeamDTO dto = new TeamDTO();
        dto.setTeamId(teamId);
        dto.setName(name);
        return dto;
    }
}
