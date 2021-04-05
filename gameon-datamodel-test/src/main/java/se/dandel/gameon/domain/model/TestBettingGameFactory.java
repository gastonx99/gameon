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

import static se.dandel.gameon.domain.model.TestTeamFactory.createTeam;

public class TestBettingGameFactory {
    private static AtomicLong PK_BET = new AtomicLong(1);

    private static final AtomicLong remoteKey = new AtomicLong(1);

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

}
