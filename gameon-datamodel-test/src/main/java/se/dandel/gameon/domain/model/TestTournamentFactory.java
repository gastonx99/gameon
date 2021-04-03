package se.dandel.gameon.domain.model;

import org.apache.commons.collections4.MapUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TestTournamentFactory {

    public static Tournament createTournamentPremierLeague20202021() {
        Tournament tournament = new Tournament(TournamentType.LEAGUE);
        tournament.setName("Premier League");

        Map<String, Team> teams = createTeamsEngland();

        Season season = new Season(tournament);
        season.setName("2020/2021");
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
        match.setMatchStart(matchDateTime);
        match.setTeams(homeTeam, awayTeam);
    }

    private static Map<String, Team> createTeamsEngland() {
        Collection<Team> c = new ArrayList<>();
        c.add(Team.of("ARSENAL", "Arsenal"));
        c.add(Team.of("ASTON_VILLA", "Aston Villa"));
        c.add(Team.of("BRIGHTON_HOVE_ALBION", "Brighton & Hove Albion"));
        c.add(Team.of("BURNLEY", "Burnley"));
        c.add(Team.of("CHELSEA", "Chelsea"));
        c.add(Team.of("CRYSTAL_PALACE", "Crystal Palace"));
        c.add(Team.of("EVERTON", "Everton"));
        c.add(Team.of("FULHAM", "Fulham"));
        c.add(Team.of("LEEDS_UNITED", "Leeds United"));
        c.add(Team.of("LEICESTER_CITY", "Leicester City"));
        c.add(Team.of("LIVERPOOL", "Liverpool"));
        c.add(Team.of("MANCHESTER_CITY", "Manchester City"));
        c.add(Team.of("MANCHESTER_UNITED", "Manchester United"));
        c.add(Team.of("NEWCASTLE_UNITED", "Newcastle United"));
        c.add(Team.of("SHEFFIELD_UNITED", "Sheffield United"));
        c.add(Team.of("SOUTHAMPTON", "Southampton"));
        c.add(Team.of("TOTTENHAM_HOTSPUR", "Tottenham Hotspur"));
        c.add(Team.of("WEST_BROMWICH_ALBION", "West Bromwich Albion"));
        c.add(Team.of("WEST_HAM_UNITED", "West Ham United"));
        c.add(Team.of("WOLVERHAMPTON_WANDERERS", "Wolverhampton Wanderers"));
        Map<String, Team> teams = new HashMap<>();
        MapUtils.populateMap(teams, c, t -> t.getName());
        return teams;
    }

    public static Tournament createTournamentWorldCup2018() {
        Tournament tournament = new Tournament(TournamentType.CUP);
        tournament.setName("Men's Football World Cup");

        Map<String, Team> teams = createTeamsWorldCup2018();

        Season season = new Season(tournament);
        season.setName("2018");

        Team homeTeam = teams.get("Russia");
        Team awayTeam = teams.get("Saudi Arabia");
        ZonedDateTime matchDateTime =
                ZonedDateTime.of(LocalDateTime.parse("2018-06-14T00:00:00"), ZoneId.systemDefault());
        Match match = new Match(season);
        match.setMatchStart(matchDateTime);
        match.setTeams(homeTeam, awayTeam);

        return tournament;
    }

    public static Map<String, Team> createTeamsWorldCup2018() {
        Collection<Team> c = new ArrayList<>();
        c.add(Team.of("ARGENTINA", "Argentina"));
        c.add(Team.of("AUSTRALIA", "Australia"));
        c.add(Team.of("BELGIUM", "Belgium"));
        c.add(Team.of("BRAZIL", "Brazil"));
        c.add(Team.of("COLOMBIA", "Colombia"));
        c.add(Team.of("COSTA_RICA", "Costa Rica"));
        c.add(Team.of("CROATIA", "Croatia"));
        c.add(Team.of("DENMARK", "Denmark"));
        c.add(Team.of("EGYPT", "Egypt"));
        c.add(Team.of("ENGLAND", "England"));
        c.add(Team.of("FRANCE", "France"));
        c.add(Team.of("GERMANY", "Germany"));
        c.add(Team.of("ICELAND", "Iceland"));
        c.add(Team.of("IRAN", "Iran"));
        c.add(Team.of("JAPAN", "Japan"));
        c.add(Team.of("MEXICO", "Mexico"));
        c.add(Team.of("MOROCCO", "Morocco"));
        c.add(Team.of("NIGERIA", "Nigeria"));
        c.add(Team.of("PANAMA", "Panama"));
        c.add(Team.of("PERU", "Peru"));
        c.add(Team.of("POLAND", "Poland"));
        c.add(Team.of("PORTUGAL", "Portugal"));
        c.add(Team.of("RUSSIA", "Russia"));
        c.add(Team.of("SAUDI_ARABIA", "Saudi Arabia"));
        c.add(Team.of("SENEGAL", "Senegal"));
        c.add(Team.of("SERBIA", "Serbia"));
        c.add(Team.of("SOUTH_KOREA", "South Korea"));
        c.add(Team.of("SPAIN", "Spain"));
        c.add(Team.of("SWEDEN", "Sweden"));
        c.add(Team.of("SWITZERLAND", "Switzerland"));
        c.add(Team.of("TUNISIA", "Tunisia"));
        c.add(Team.of("URUGUAY", "Uruguay"));

        Map<String, Team> teams = new HashMap<>();
        MapUtils.populateMap(teams, c, t -> t.getName());
        return teams;
    }

}
