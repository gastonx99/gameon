package se.dandel.gameon.domain.model;

import org.apache.commons.collections4.MapUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static se.dandel.gameon.domain.model.TestTeamFactory.createTeam;

public class TestTournamentFactory {
    private static final AtomicLong REMOTE_KEY = new AtomicLong(1);

    public static Tournament createTournamentPremierLeague20202021() {
        Tournament tournament = createTournament(TournamentType.LEAGUE, "Premier League");

        Map<String, Team> teams = createTeamsEngland();

        Season season = new Season();
        season.setRemoteKey(RemoteKey.of(REMOTE_KEY.getAndIncrement()));
        season.setTournament(tournament);
        season.setName("2020/2021");
        tournament.addSeason(season);
        createMatch(season, LocalDateTime.parse("2020-09-14T15:00:00"), teams.get("ARSENAL"), teams.get("ASTON_VILLA"));
        createMatch(season, LocalDateTime.parse("2020-09-14T15:00:00"), teams.get("BRIGHTON_HOVE_ALBION"), teams.get("BURNLEY"));
        createMatch(season, LocalDateTime.parse("2020-09-14T15:00:00"), teams.get("CHELSEA"), teams.get("CRYSTAL_PALACE"));
        createMatch(season, LocalDateTime.parse("2020-09-14T15:00:00"), teams.get("EVERTON"), teams.get("FULHAM"));
        createMatch(season, LocalDateTime.parse("2020-09-14T15:00:00"), teams.get("LEEDS_UNITED"), teams.get("LEICESTER_CITY"));
        createMatch(season, LocalDateTime.parse("2020-09-14T15:00:00"), teams.get("LIVERPOOL"), teams.get("MANCHESTER_CITY"));

        createMatch(season, LocalDateTime.parse("2020-09-21T15:00:00"), teams.get("ARSENAL"), teams.get("BRIGHTON_HOVE_ALBION"));
        createMatch(season, LocalDateTime.parse("2020-09-21T15:00:00"), teams.get("ASTON_VILLA"), teams.get("BURNLEY"));
        createMatch(season, LocalDateTime.parse("2020-09-21T15:00:00"), teams.get("CHELSEA"), teams.get("EVERTON"));
        createMatch(season, LocalDateTime.parse("2020-09-21T15:00:00"), teams.get("CRYSTAL_PALACE"), teams.get("FULHAM"));
        createMatch(season, LocalDateTime.parse("2020-09-21T15:00:00"), teams.get("LEEDS_UNITED"), teams.get("LIVERPOOL"));
        createMatch(season, LocalDateTime.parse("2020-09-21T15:00:00"), teams.get("LEICESTER_CITY"), teams.get("MANCHESTER_CITY"));

        return tournament;
    }


    private static Map<String, Team> createTeamsEngland() {
        Collection<Team> c = new ArrayList<>();
        c.add(createTeam("Arsenal"));
        c.add(createTeam("Aston Villa"));
        c.add(createTeam("Brighton & Hove Albion"));
        c.add(createTeam("Burnley"));
        c.add(createTeam("Chelsea"));
        c.add(createTeam("Crystal Palace"));
        c.add(createTeam("Everton"));
        c.add(createTeam("Fulham"));
        c.add(createTeam("Leeds United"));
        c.add(createTeam("Leicester City"));
        c.add(createTeam("Liverpool"));
        c.add(createTeam("Manchester City"));
        c.add(createTeam("Manchester United"));
        c.add(createTeam("Newcastle United"));
        c.add(createTeam("Sheffield United"));
        c.add(createTeam("Southampton"));
        c.add(createTeam("Tottenham Hotspur"));
        c.add(createTeam("West Bromwich Albion"));
        c.add(createTeam("West Ham United"));
        c.add(createTeam("Wolverhampton Wanderers"));
        Map<String, Team> teams = new HashMap<>();
        MapUtils.populateMap(teams, c, t -> t.getName());
        return teams;
    }

    public static Tournament createTournamentWorldCup2018() {
        Tournament tournament = createTournament(TournamentType.CUP, "Men's Football World Cup");
        tournament.setRemoteKey(RemoteKey.of(REMOTE_KEY.getAndIncrement()));

        Map<String, Team> teams = createTeamsWorldCup2018();

        Season season = new Season();
        season.setRemoteKey(RemoteKey.of(REMOTE_KEY.getAndIncrement()));
        season.setTournament(tournament);
        season.setName("2018");
        tournament.addSeason(season);

        Team homeTeam = teams.get("Russia");
        Team awayTeam = teams.get("Saudi Arabia");
        LocalDateTime matchStart = LocalDateTime.parse("2018-06-14T00:00:00");
        RemoteKey remoteKey = RemoteKey.of(REMOTE_KEY.getAndIncrement());
        createMatch(season, homeTeam, awayTeam, matchStart, remoteKey);

        return tournament;
    }

    public static Map<String, Team> createTeamsWorldCup2018() {
        Collection<Team> c = new ArrayList<>();
        c.add(createTeam("Argentina"));
        c.add(createTeam("Australia"));
        c.add(createTeam("Belgium"));
        c.add(createTeam("Brazil"));
        c.add(createTeam("Colombia"));
        c.add(createTeam("Costa Rica"));
        c.add(createTeam("Croatia"));
        c.add(createTeam("Denmark"));
        c.add(createTeam("Egypt"));
        c.add(createTeam("England"));
        c.add(createTeam("France"));
        c.add(createTeam("Germany"));
        c.add(createTeam("Iceland"));
        c.add(createTeam("Iran"));
        c.add(createTeam("Japan"));
        c.add(createTeam("Mexico"));
        c.add(createTeam("Morocco"));
        c.add(createTeam("Nigeria"));
        c.add(createTeam("Panama"));
        c.add(createTeam("Peru"));
        c.add(createTeam("Poland"));
        c.add(createTeam("Portugal"));
        c.add(createTeam("Russia"));
        c.add(createTeam("Saudi Arabia"));
        c.add(createTeam("Senegal"));
        c.add(createTeam("Serbia"));
        c.add(createTeam("South Korea"));
        c.add(createTeam("Spain"));
        c.add(createTeam("Sweden"));
        c.add(createTeam("Switzerland"));
        c.add(createTeam("Tunisia"));
        c.add(createTeam("Uruguay"));

        Map<String, Team> teams = new HashMap<>();
        MapUtils.populateMap(teams, c, t -> t.getName());
        return teams;
    }

    private static Tournament createTournament(TournamentType type, String name) {
        Tournament tournament = new Tournament(type);
        tournament.setName(name);
        tournament.setCountry(TestCountryFactory.createCountry());
        tournament.setRemoteKey(RemoteKey.of(REMOTE_KEY.getAndIncrement()));
        return tournament;
    }

    private static void createMatch(Season season, LocalDateTime matchDateTime, Team homeTeam, Team awayTeam) {
        createMatch(season, homeTeam, awayTeam, matchDateTime, RemoteKey.of(REMOTE_KEY.getAndIncrement()));
    }

    private static Match createMatch(Season season, Team homeTeam, Team awayTeam, LocalDateTime matchStart, RemoteKey remoteKey) {
        Match match = new Match();
        match.setStatus(MatchStatus.NOT_STARTED);
        match.setSeason(season);
        season.addMatch(match);
        match.setRemoteKey(remoteKey);
        match.setMatchStart(matchStart);
        match.setHomeTeam(homeTeam);
        match.setAwayTeam(awayTeam);
        return match;
    }

}
