package se.dandel.gameon.domain.model;

import org.apache.commons.collections4.MapUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static se.dandel.gameon.domain.model.TestTeamFactory.createTeam;

public class TestTournamentFactory {

    private static final AtomicLong remoteKey = new AtomicLong(1);

    public static Tournament createTournamentPremierLeague20202021() {
        Tournament tournament = new Tournament(TournamentType.LEAGUE);
        tournament.setRemoteKey(RemoteKey.of(remoteKey.getAndIncrement()));
        tournament.setName("Premier League");

        Map<String, Team> teams = createTeamsEngland();

        Season season = new Season();
        season.setRemoteKey(RemoteKey.of(remoteKey.getAndIncrement()));
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

    private static void createMatch(Season season, LocalDateTime localDateTime, Team homeTeam, Team awayTeam) {
        ZonedDateTime matchDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        Match match = new Match(season);
        match.setRemoteKey(RemoteKey.of(remoteKey.getAndIncrement()));
        match.setMatchStart(matchDateTime);
        match.setTeams(homeTeam, awayTeam);
    }

    private static Map<String, Team> createTeamsEngland() {
        Collection<Team> c = new ArrayList<>();
        c.add(createTeam("ARSENAL", "Arsenal"));
        c.add(createTeam("ASTON_VILLA", "Aston Villa"));
        c.add(createTeam("BRIGHTON_HOVE_ALBION", "Brighton & Hove Albion"));
        c.add(createTeam("BURNLEY", "Burnley"));
        c.add(createTeam("CHELSEA", "Chelsea"));
        c.add(createTeam("CRYSTAL_PALACE", "Crystal Palace"));
        c.add(createTeam("EVERTON", "Everton"));
        c.add(createTeam("FULHAM", "Fulham"));
        c.add(createTeam("LEEDS_UNITED", "Leeds United"));
        c.add(createTeam("LEICESTER_CITY", "Leicester City"));
        c.add(createTeam("LIVERPOOL", "Liverpool"));
        c.add(createTeam("MANCHESTER_CITY", "Manchester City"));
        c.add(createTeam("MANCHESTER_UNITED", "Manchester United"));
        c.add(createTeam("NEWCASTLE_UNITED", "Newcastle United"));
        c.add(createTeam("SHEFFIELD_UNITED", "Sheffield United"));
        c.add(createTeam("SOUTHAMPTON", "Southampton"));
        c.add(createTeam("TOTTENHAM_HOTSPUR", "Tottenham Hotspur"));
        c.add(createTeam("WEST_BROMWICH_ALBION", "West Bromwich Albion"));
        c.add(createTeam("WEST_HAM_UNITED", "West Ham United"));
        c.add(createTeam("WOLVERHAMPTON_WANDERERS", "Wolverhampton Wanderers"));
        Map<String, Team> teams = new HashMap<>();
        MapUtils.populateMap(teams, c, t -> t.getName());
        return teams;
    }

    public static Tournament createTournamentWorldCup2018() {
        Tournament tournament = new Tournament(TournamentType.CUP);
        tournament.setRemoteKey(RemoteKey.of(remoteKey.getAndIncrement()));
        tournament.setName("Men's Football World Cup");

        Map<String, Team> teams = createTeamsWorldCup2018();

        Season season = new Season();
        season.setRemoteKey(RemoteKey.of(remoteKey.getAndIncrement()));
        season.setTournament(tournament);
        season.setName("2018");
        tournament.addSeason(season);

        Team homeTeam = teams.get("Russia");
        Team awayTeam = teams.get("Saudi Arabia");
        ZonedDateTime matchDateTime =
                ZonedDateTime.of(LocalDateTime.parse("2018-06-14T00:00:00"), ZoneId.systemDefault());
        Match match = new Match(season);
        match.setRemoteKey(RemoteKey.of(remoteKey.getAndIncrement()));
        match.setMatchStart(matchDateTime);
        match.setTeams(homeTeam, awayTeam);

        return tournament;
    }

    public static Map<String, Team> createTeamsWorldCup2018() {
        Collection<Team> c = new ArrayList<>();
        c.add(createTeam("ARGENTINA", "Argentina"));
        c.add(createTeam("AUSTRALIA", "Australia"));
        c.add(createTeam("BELGIUM", "Belgium"));
        c.add(createTeam("BRAZIL", "Brazil"));
        c.add(createTeam("COLOMBIA", "Colombia"));
        c.add(createTeam("COSTA_RICA", "Costa Rica"));
        c.add(createTeam("CROATIA", "Croatia"));
        c.add(createTeam("DENMARK", "Denmark"));
        c.add(createTeam("EGYPT", "Egypt"));
        c.add(createTeam("ENGLAND", "England"));
        c.add(createTeam("FRANCE", "France"));
        c.add(createTeam("GERMANY", "Germany"));
        c.add(createTeam("ICELAND", "Iceland"));
        c.add(createTeam("IRAN", "Iran"));
        c.add(createTeam("JAPAN", "Japan"));
        c.add(createTeam("MEXICO", "Mexico"));
        c.add(createTeam("MOROCCO", "Morocco"));
        c.add(createTeam("NIGERIA", "Nigeria"));
        c.add(createTeam("PANAMA", "Panama"));
        c.add(createTeam("PERU", "Peru"));
        c.add(createTeam("POLAND", "Poland"));
        c.add(createTeam("PORTUGAL", "Portugal"));
        c.add(createTeam("RUSSIA", "Russia"));
        c.add(createTeam("SAUDI_ARABIA", "Saudi Arabia"));
        c.add(createTeam("SENEGAL", "Senegal"));
        c.add(createTeam("SERBIA", "Serbia"));
        c.add(createTeam("SOUTH_KOREA", "South Korea"));
        c.add(createTeam("SPAIN", "Spain"));
        c.add(createTeam("SWEDEN", "Sweden"));
        c.add(createTeam("SWITZERLAND", "Switzerland"));
        c.add(createTeam("TUNISIA", "Tunisia"));
        c.add(createTeam("URUGUAY", "Uruguay"));

        Map<String, Team> teams = new HashMap<>();
        MapUtils.populateMap(teams, c, t -> t.getName());
        return teams;
    }

}
