package se.dandel.gameon.domain.model;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.Comparator.comparing;

public class TestTournamentFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestTournamentFactory.class);

    private static final AtomicLong REMOTE_KEY = new AtomicLong(1);

    public static final LocalTime AFTERNOON = LocalTime.of(19, 0);

    public static final LocalTime EVENING = LocalTime.of(21, 0);

    public static Tournament createTournamentPremierLeague20202021() {
        Tournament tournament = createTournament(TournamentType.LEAGUE, "Premier League");
        Season season = createSeason(tournament, "2020/2021", LocalDate.parse("2020-09-10"));
        List<Team> teams = TestTeamFactory.createTeamsEngland2021();
        // Sort in predictable, but not apparent alphabetical, order
        teams.sort(comparing(team -> StringUtils.reverse(team.getName())));
        createRoundRobinMatches(season, teams, teams.size(), 7);
        return tournament;
    }

    public static Tournament createTournamentWorldCup2018() {
        Tournament tournament = createTournament(TournamentType.CUP, "Men's Football World Cup");
        tournament.setRemoteKey(RemoteKey.of(REMOTE_KEY.getAndIncrement()));

        Season season = createSeason(tournament, "2018", LocalDate.parse("2018-06-20"));

        List<Team> teams = TestTeamFactory.createTeamsWorldcup2018();
        // Sort in predictable, but not apparent alphabetical, order
        teams.sort(comparing(team -> StringUtils.reverse(team.getName())));
        int groupSize = 4;
        createRoundRobinMatches(season, teams, groupSize, 0);
        createPlayoffMatches(season);
        return tournament;
    }

    public static Tournament createTournamentEuro2021() {
        Tournament tournament = createTournament(TournamentType.CUP, "Men's Football Euro");
        tournament.setRemoteKey(RemoteKey.of(REMOTE_KEY.getAndIncrement()));

        Season season = createSeason(tournament, "2020", LocalDate.parse("2021-06-11"));
        season.setEndDate(LocalDate.parse("2021-07-12"));

        List<Team> teams = TestTeamFactory.createTeamsEuro2020();
        // Sort in predictable, but not apparent alphabetical, order
        teams.sort(comparing(team -> StringUtils.reverse(team.getName())));
        int groupSize = 4;
        createRoundRobinMatches(season, teams, groupSize, 0);
        createPlayoffMatches(season);
        return tournament;
    }

    private static Season createSeason(Tournament tournament, String seasonName, LocalDate seasonStartDate) {
        Season season = new Season();
        season.setRemoteKey(RemoteKey.of(REMOTE_KEY.getAndIncrement()));
        season.setTournament(tournament);
        season.setName(seasonName);
        season.setStartDate(seasonStartDate);
        tournament.addSeason(season);
        return season;
    }

    private static void createRoundRobinMatches(Season season, List<Team> teams, int groupSize, int daysBetweenRounds) {
        int numGroups = teams.size() / groupSize;
        List<List<Team>> groupsOfTeams = ListUtils.partition(teams, groupSize);
        for (int groupIndex = 0; groupIndex < groupsOfTeams.size(); groupIndex++) {
            List<Team> teamsInGroup = groupsOfTeams.get(groupIndex);
            createRoundRobinMatchesForGroup(season, groupIndex, numGroups, teamsInGroup, daysBetweenRounds);
        }
    }

    private static void createRoundRobinMatchesForGroup(Season season, int groupIndex, int numGroups, List<Team> teams, int daysBetweenRounds) {
        String stage = "Group stage";
        String group = "" + (char) ('A' + groupIndex);

        int rounds = (teams.size() - 1); // Days needed to complete tournament
        int matchesPerRound = teams.size() / 2;

        List<Team> teamList = new ArrayList<>(teams);
        teamList.remove(0);

        int teamsSize = teamList.size();

        for (int round = 0; round < rounds; round++) {
            LOGGER.trace("Day {}", (round + 1));

            int teamIdx = round % teamsSize;

            Team homeTeam = teamList.get(teamIdx);
            Team awayTeam = teams.get(0);
            LOGGER.trace("{} vs {}", homeTeam, awayTeam);
            LocalDateTime matchStart = getMatchStart(season, groupIndex, numGroups, daysBetweenRounds, round);
            Match match = createMatch(season, stage, group, String.valueOf(round + 1), homeTeam, awayTeam, matchStart);
            LOGGER.trace("Match: {}", match);

            for (int idx = 1; idx < matchesPerRound; idx++) {
                int firstTeam = (round + idx) % teamsSize;
                int secondTeam = (round + teamsSize - idx) % teamsSize;
                homeTeam = teamList.get(firstTeam);
                awayTeam = teamList.get(secondTeam);
                LOGGER.trace("{} vs {}", homeTeam, awayTeam);
                matchStart = getMatchStart(season, groupIndex, numGroups, daysBetweenRounds, round);
                match = createMatch(season, stage, group, String.valueOf(round + 1), homeTeam, awayTeam, matchStart);
                LOGGER.trace("Match: {}", match);
            }
        }
    }

    private static void createPlayoffMatches(Season season) {
        LocalDate maxMatchDay = season.getMatches().stream().max(comparing(Match::getMatchStart)).get().getMatchStart().toLocalDate();
        String stage = "Playoffs";
        String group = "Knockout stage";
        String round = "1";
        LocalDate playoffMatchDay = maxMatchDay.plusDays(2);
        for (int i1 = 0; i1 < 8; i1++) {
            boolean even = (i1 % 2) == 0;
            createMatch(season, stage, group, round, playoffMatchDay.atTime(even ? AFTERNOON : EVENING));
            if (!even) {
                playoffMatchDay = playoffMatchDay.plusDays(1);
            }
        }
        round = "Quarter-finals";
        playoffMatchDay = playoffMatchDay.plusDays(1);
        for (int i1 = 0; i1 < 4; i1++) {
            boolean even = (i1 % 2) == 0;
            createMatch(season, stage, group, round, playoffMatchDay.atTime(even ? AFTERNOON : EVENING));
            playoffMatchDay = playoffMatchDay.plusDays(1);
        }
        round = "Semi-finals";
        playoffMatchDay = playoffMatchDay.plusDays(1);
        createMatch(season, stage, group, round, playoffMatchDay.atTime(EVENING));
        playoffMatchDay = playoffMatchDay.plusDays(1);
        createMatch(season, stage, group, round, playoffMatchDay.atTime(EVENING));

        round = "Final";
        playoffMatchDay = playoffMatchDay.plusDays(2);
        createMatch(season, stage, group, round, playoffMatchDay.atTime(EVENING));

    }

    private static LocalDateTime getMatchStart(Season season, int groupIndex, int numGroups, int daysBetweenRounds, int round) {
        LocalDate matchDay = season.getStartDate().plusDays(numGroups / 2 * round + daysBetweenRounds * round + groupIndex);
        LocalTime matchTime = (groupIndex % 2) == 0 ? AFTERNOON : EVENING;
        LocalDateTime matchStart = matchDay.atTime(matchTime);
        return matchStart;
    }

    private static Tournament createTournament(TournamentType type, String name) {
        Tournament tournament = new Tournament(type);
        tournament.setName(name);
        tournament.setCountry(TestCountryFactory.createCountry());
        tournament.setRemoteKey(RemoteKey.of(REMOTE_KEY.getAndIncrement()));
        return tournament;
    }

    private static void createMatch(Season season, String stage, String group, String round, LocalDateTime matchStart) {
        Match match = createMatch(season, stage, group, round, null, null, matchStart);
    }


    private static Match createMatch(Season season, String stage, String group, String round, Team homeTeam, Team awayTeam, LocalDateTime matchStart) {
        RemoteKey remoteKey = RemoteKey.of(REMOTE_KEY.getAndIncrement());
        Match match = new Match();
        match.setStatus(MatchStatus.NOT_STARTED);
        match.setSeason(season);
        season.addMatch(match);
        match.setRemoteKey(remoteKey);
        match.setMatchStart(matchStart);
        match.setHomeTeam(homeTeam);
        match.setAwayTeam(awayTeam);
        match.setStage(stage);
        match.setGroup(group);
        match.setRound(round);
        return match;
    }

}
