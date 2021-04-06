package se.dandel.gameon.adapter.out.rest.api1;

import org.junit.jupiter.api.Test;
import se.dandel.gameon.domain.model.Match;
import se.dandel.gameon.domain.model.MatchStatus;

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
        assertThat(actual.getStatus(), is(equalTo(MatchStatus.INPLAY)));
        assertThat(actual.getStatustext(), is(equalTo("Event is in break waiting for extra time or penalties.")));
        assertThat(actual.getStage(), is(equalTo(expected.getStage().getName())));
        assertThat(actual.getGroup(), is(equalTo(expected.getGroup().getGroupName())));
        assertThat(actual.getRound(), is(equalTo(expected.getRound().getName())));
    }

    private MatchDTO createMatchDTO() {
        MatchDTO dto = new MatchDTO();
        dto.setStatusCode(14);
        dto.setMatchStart(MATCH_START);
        dto.setHomeTeam(createTeamDTO(17, "Hemmalaget"));
        dto.setAwayTeam(createTeamDTO(71, "Bortalaget"));
        dto.setStats(createStatsDTO(3, 4));
        dto.setStage(createStageDTO("Group stage"));
        dto.setGroup(createGroupDTO("Grupp A"));
        dto.setRound(createRoundDTO("Round 2"));
        return dto;
    }

    private MatchDTO.RoundDTO createRoundDTO(String name) {
        MatchDTO.RoundDTO dto = new MatchDTO.RoundDTO();
        dto.setName(name);
        return dto;
    }

    private MatchDTO.StageDTO createStageDTO(String name) {
        MatchDTO.StageDTO dto = new MatchDTO.StageDTO();
        dto.setName(name);
        return dto;
    }

    private MatchDTO.GroupDTO createGroupDTO(String name) {
        MatchDTO.GroupDTO dto = new MatchDTO.GroupDTO();
        dto.setGroupName(name);
        return dto;
    }

    private MatchDTO.StatsDTO createStatsDTO(int homeScore, int awayScore) {
        MatchDTO.StatsDTO dto = new MatchDTO.StatsDTO();
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
