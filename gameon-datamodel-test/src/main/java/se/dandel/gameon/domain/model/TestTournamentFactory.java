package se.dandel.gameon.domain.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;

public class TestTournamentFactory {

    public static Tournament createTournament() {
        Tournament tournament = new Tournament(TournamentType.CUP);
        tournament.setName("Men's Football World Cup");

        Map<String, Team> teams = createTeamsWorldCup2018();

        Season season = new Season(tournament);
        season.setName("2018");

        Team homeTeam = teams.get("RUSSIA");
        Team awayTeam = teams.get("SAUDI_ARABIA");
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
        c.add(Team.of("BRAZIL", " Brazil"));
        c.add(Team.of("COLOMBIA", " Colombia"));
        c.add(Team.of("COSTA_RICA", " Costa Rica"));
        c.add(Team.of("CROATIA", "Croatia"));
        c.add(Team.of("DENMARK", "Denmark"));
        c.add(Team.of("EGYPT", "Egypt"));
        c.add(Team.of("ENGLAND", "England"));
        c.add(Team.of("FRANCE", " France"));
        c.add(Team.of("GERMANY", "Germany"));
        c.add(Team.of("ICELAND", "Iceland"));
        c.add(Team.of("IRAN", " Iran"));
        c.add(Team.of("JAPAN", "Japan"));
        c.add(Team.of("MEXICO", " Mexico"));
        c.add(Team.of("MOROCCO", "Morocco"));
        c.add(Team.of("NIGERIA", "Nigeria"));
        c.add(Team.of("PANAMA", " Panama"));
        c.add(Team.of("PERU", " Peru"));
        c.add(Team.of("POLAND", " Poland"));
        c.add(Team.of("PORTUGAL", " Portugal"));
        c.add(Team.of("RUSSIA", " Russia"));
        c.add(Team.of("SAUDI_ARABIA", " Saudi Arabia"));
        c.add(Team.of("SENEGAL", "Senegal"));
        c.add(Team.of("SERBIA", " Serbia"));
        c.add(Team.of("SOUTH_KOREA", "South Korea"));
        c.add(Team.of("SPAIN", "Spain"));
        c.add(Team.of("SWEDEN", " Sweden"));
        c.add(Team.of("SWITZERLAND", "Switzerland"));
        c.add(Team.of("TUNISIA", "Tunisia"));
        c.add(Team.of("URUGUAY", "Uruguay"));

        Map<String, Team> teams = new HashMap<>();
        MapUtils.populateMap(teams, c, t -> t.getKey());
        return teams;
    }

}
