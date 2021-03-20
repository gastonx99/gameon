package se.dandel.gameon.domain.model;

import org.apache.commons.collections4.MapUtils;
import se.dandel.gameon.domain.model.bet.Bet;
import se.dandel.gameon.domain.model.bet.BettingGame;
import se.dandel.gameon.domain.model.bet.BettingGameUser;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class TestBettingGameFactory {
    private static AtomicLong PK_BET = new AtomicLong(1);

    public static BettingGame createBettingGamePremierLeague20202021() {
        User owner = TestUserFactory.createUser();
        Tournament tournament = TestTournamentFactory.createTournamentPremierLeague20202021();
        BettingGame bettingGame = new BettingGame(tournament.getSeason("2020/2021"), owner, "Gurras Premier League 2020/2021");
        User participant = TestUserFactory.createUser("kallekula");
        BettingGameUser bettingGameUser = createBettingGameUser(participant, bettingGame);
        bettingGame.addParticipant(bettingGameUser);
        return bettingGame;
    }

    private static BettingGameUser createBettingGameUser(User participant, BettingGame bettingGame) {
        BettingGameUser bettingGameUser = new BettingGameUser(bettingGame, participant);
        Collection<Match> matches = bettingGame.getSeason().getMatches();
        for (Match match : matches) {
            Bet bet = bettingGameUser.addBet(match, 1, 0);
            bet.setPk(PK_BET.getAndIncrement());
        }
        return bettingGameUser;
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
        MapUtils.populateMap(teams, c, t -> t.getKey());
        return teams;
    }

}
