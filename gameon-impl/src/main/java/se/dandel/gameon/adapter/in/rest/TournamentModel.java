package se.dandel.gameon.adapter.in.rest;

import java.util.Collection;

public class TournamentModel {
    public long pk;

    public String name;

    public String countryName;

    public String countryCode;

    public String countryContinent;


    public Collection<SeasonModel> seasons;

    public static class SeasonModel {
        public long pk;

        public String name;

        public String status;

        public Collection<MatchModel> matches;
    }

    public static class MatchModel {
        public long pk;

        public TeamModel homeTeam;

        public TeamModel awayTeam;

        public String matchStart;

        public String status;

        public String statustext;

        public String stage;

        public String group;

        public String round;

        public VenueModel venue;
    }

    public static class TeamModel {
        public long pk;

        public String name;

        public String shortCode;

        public String logo;

        public String countryName;

        public String countryCode;

        public String countryContinent;

    }

    public static class VenueModel {

        public long pk;

        public String name;

        public String capacity;

        public String city;

        public String countryCode;
    }

}
