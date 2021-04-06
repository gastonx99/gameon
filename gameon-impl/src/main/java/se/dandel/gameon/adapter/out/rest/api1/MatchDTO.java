package se.dandel.gameon.adapter.out.rest.api1;

import org.apache.commons.lang3.builder.RecursiveToStringStyle;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class MatchDTO {

    private int matchId;

    private int statusCode;

    private String status;

    private String matchStart;

    private int leagueId;

    private int seasonId;

    private TeamDTO homeTeam;

    private TeamDTO awayTeam;

    private StatsDTO stats;

    private StageDTO stage;

    private GroupDTO group;

    private RoundDTO round;

    private VenueDTO venue;

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMatchStart() {
        return matchStart;
    }

    public void setMatchStart(String matchStart) {
        this.matchStart = matchStart;
    }

    public int getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(int leagueId) {
        this.leagueId = leagueId;
    }

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    public TeamDTO getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(TeamDTO homeTeam) {
        this.homeTeam = homeTeam;
    }

    public TeamDTO getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(TeamDTO awayTeam) {
        this.awayTeam = awayTeam;
    }

    public StatsDTO getStats() {
        return stats;
    }

    public void setStats(StatsDTO stats) {
        this.stats = stats;
    }

    public StageDTO getStage() {
        return stage;
    }

    public void setStage(StageDTO stage) {
        this.stage = stage;
    }

    public GroupDTO getGroup() {
        return group;
    }

    public void setGroup(GroupDTO group) {
        this.group = group;
    }

    public RoundDTO getRound() {
        return round;
    }

    public void setRound(RoundDTO round) {
        this.round = round;
    }

    public VenueDTO getVenue() {
        return venue;
    }

    public void setVenue(VenueDTO venue) {
        this.venue = venue;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, RecursiveToStringStyle.SHORT_PREFIX_STYLE);
    }

    public static class StageDTO {
        private int stageId;

        private String name;

        public int getStageId() {
            return stageId;
        }

        public void setStageId(int stageId) {
            this.stageId = stageId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class GroupDTO {
        private int groupId;

        private String groupName;

        public int getGroupId() {
            return groupId;
        }

        public void setGroupId(int groupId) {
            this.groupId = groupId;
        }

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }
    }

    public static class RoundDTO {
        private int roundId;

        private String name;

        public int getRoundId() {
            return roundId;
        }

        public void setRoundId(int roundId) {
            this.roundId = roundId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class StatsDTO {

        private int homeScore;

        private int awayScore;

        public int getHomeScore() {
            return homeScore;
        }

        public void setHomeScore(int homeScore) {
            this.homeScore = homeScore;
        }

        public int getAwayScore() {
            return awayScore;
        }

        public void setAwayScore(int awayScore) {
            this.awayScore = awayScore;
        }

        @Override
        public String toString() {
            return ReflectionToStringBuilder.toString(this, RecursiveToStringStyle.SHORT_PREFIX_STYLE);
        }

    }
}
