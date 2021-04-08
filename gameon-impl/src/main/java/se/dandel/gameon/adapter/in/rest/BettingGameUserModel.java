package se.dandel.gameon.adapter.in.rest;

import java.util.ArrayList;
import java.util.Collection;

public class BettingGameUserModel {

    public long pk;

    public String name;

    public String bettingGameOwner;

    public String tournament;

    public String season;

    public Collection<BetModel> bets = new ArrayList<>();

    public static class BetModel {

        public long pk;

        public int homeScore;

        public int awayScore;

        public MatchModel match;
    }

    public static class MatchModel {
        public TeamModel homeTeam;

        public TeamModel awayTeam;

        public String matchStart;

        public int finalHomeScore;

        public int finalAwayScore;
    }


    public static class TeamModel {
        public String name;

        public String countryCode;

        public String logo;
    }
}
