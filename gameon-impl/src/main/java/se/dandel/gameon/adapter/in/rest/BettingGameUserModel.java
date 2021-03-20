package se.dandel.gameon.adapter.in.rest;

import java.util.ArrayList;
import java.util.Collection;

public class BettingGameUserModel {

    private long pk;

    private String name;

    private String bettingGameOwner;

    private String tournament;

    private String season;

    private Collection<BetModel> bets = new ArrayList<>();

    public static class BetModel {

        private long pk;

        private int homeScore;

        private int awayScore;

        private MatchModel match;

        public long getPk() {
            return pk;
        }

        public void setPk(long pk) {
            this.pk = pk;
        }

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

        public MatchModel getMatch() {
            return match;
        }

        public void setMatch(MatchModel match) {
            this.match = match;
        }
    }

    public static class MatchModel {
        private TeamModel homeTeam;

        private TeamModel awayTeam;

        private String matchStart;

        private int finalHomeScore;

        private int finalAwayScore;

        public TeamModel getHomeTeam() {
            return homeTeam;
        }

        public void setHomeTeam(TeamModel homeTeam) {
            this.homeTeam = homeTeam;
        }

        public TeamModel getAwayTeam() {
            return awayTeam;
        }

        public void setAwayTeam(TeamModel awayTeam) {
            this.awayTeam = awayTeam;
        }

        public String getMatchStart() {
            return matchStart;
        }

        public void setMatchStart(String matchStart) {
            this.matchStart = matchStart;
        }

        public int getFinalHomeScore() {
            return finalHomeScore;
        }

        public void setFinalHomeScore(int finalHomeScore) {
            this.finalHomeScore = finalHomeScore;
        }

        public int getFinalAwayScore() {
            return finalAwayScore;
        }

        public void setFinalAwayScore(int finalAwayScore) {
            this.finalAwayScore = finalAwayScore;
        }
    }


    public static class TeamModel {
        private String name;

        private String countryCode;

        private String logo;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }
    }

    public long getPk() {
        return pk;
    }

    public void setPk(long pk) {
        this.pk = pk;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBettingGameOwner() {
        return bettingGameOwner;
    }

    public void setBettingGameOwner(String bettingGameOwner) {
        this.bettingGameOwner = bettingGameOwner;
    }

    public String getTournament() {
        return tournament;
    }

    public void setTournament(String tournament) {
        this.tournament = tournament;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public Collection<BetModel> getBets() {
        return bets;
    }

    public void setBets(Collection<BetModel> bets) {
        this.bets = bets;
    }
}
