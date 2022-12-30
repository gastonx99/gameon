package se.dandel.gameon.domain.model;

import org.apache.commons.collections4.MapUtils;
import se.dandel.gameon.domain.model.bet.Bet;
import se.dandel.gameon.domain.model.bet.BettingGame;
import se.dandel.gameon.domain.model.bet.BettingGameUser;

import java.time.LocalDateTime;
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
        Tournament tournament = TestTournamentFactory.createTournamentPremierLeague20202021();
        Season season = tournament.getSeason("2020/2021");
        String name = "Gurras Premier League 2020/2021";
        return createBettingGame(season, name);
    }

    public static BettingGame createBettingGameWorldCup2018() {
        Tournament tournament = TestTournamentFactory.createCupWorldCup2018();
        Season season = tournament.getSeason("2018");
        String name = "Gurras World Cup 2018";
        return createBettingGame(season, name);
    }

    public static BettingGame createBettingGameEuro2021() {
        Tournament tournament = TestTournamentFactory.createCupEuro2021();
        Season season = tournament.getSeason("2020");
        String name = "Gurras Euro 2021";
        return createBettingGame(season, name);
    }

    private static BettingGame createBettingGame(Season season, String name) {
        User owner = TestUserFactory.createUser();
        BettingGame bettingGame = new BettingGame();
        bettingGame.setSeason(season);
        bettingGame.setOwner(owner);
        bettingGame.setName(name);
        User participant = TestUserFactory.createUser("kallekula");
        BettingGameUser bettingGameUser = createBettingGameUser(participant, bettingGame);
        bettingGame.addParticipant(bettingGameUser);
        return bettingGame;
    }

    private static BettingGameUser createBettingGameUser(User participant, BettingGame bettingGame) {
        BettingGameUser bettingGameUser = new BettingGameUser();
        bettingGameUser.setUser(participant);
        bettingGameUser.setBettingGame(bettingGame);
        bettingGame.addParticipant(bettingGameUser);
        Collection<Match> matches = bettingGame.getSeason().getMatches();
        for (Match match : matches) {
            Bet bet = bettingGameUser.addBet(match, 1, 0);
            bet.setPk(PK_BET.getAndIncrement());
        }
        return bettingGameUser;
    }

    private static void createMatch(Season season, LocalDateTime matchStart, Team homeTeam, Team awayTeam) {
        Match match = new Match();
        match.setSeason(season);
        season.addMatch(match);
        match.setRemoteKey(RemoteKey.of(remoteKey.getAndIncrement()));
        match.setMatchStart(matchStart);
        match.setHomeTeam(homeTeam);
        match.setAwayTeam(awayTeam);
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

}
