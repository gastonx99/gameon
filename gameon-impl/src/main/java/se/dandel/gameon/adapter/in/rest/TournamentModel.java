package se.dandel.gameon.adapter.in.rest;

import java.util.Collection;
import java.util.Collections;

public class TournamentModel {
    private String name;

    private Collection<SeasonModel> seasons;

    public static class SeasonModel {
        private String name;

        private Collection<MatchModel> matches;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setMatches(Collection<MatchModel> matches) {
            this.matches = matches;
        }

        public Collection<MatchModel> getMatches() {
            return Collections.unmodifiableCollection(matches);
        }
    }

    public static class MatchModel {
        private TeamModel hometeam;

        private TeamModel awayTeam;

        private String dateTime;

        private VenueModel venue;

        public void setHomeTeam(TeamModel hometeam) {
            this.hometeam = hometeam;
        }

        public TeamModel getHometeam() {
            return hometeam;
        }

        public void setAwayTeam(TeamModel awayTeam) {
            this.awayTeam = awayTeam;
        }

        public TeamModel getAwayTeam() {
            return awayTeam;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }

        public String getDateTime() {
            return dateTime;
        }

        public void setVenue(VenueModel venue) {
            this.venue = venue;
        }
    }

    public static class TeamModel {
        private long pk;

        private String key;

        private String name;

        private String countryCode;

        private String logo;

        public long getPk() {
            return pk;
        }

        public void setPk(long pk) {
            this.pk = pk;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

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

    public static class VenueModel {

        private long pk;

        private String name;

        private String capacity;

        private String city;

        private String countryCode;

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

        public String getCapacity() {
            return capacity;
        }

        public void setCapacity(String capacity) {
            this.capacity = capacity;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSeasons(Collection<SeasonModel> seasons) {
        this.seasons = seasons;
    }

    public Collection<SeasonModel> getSeasons() {
        return Collections.unmodifiableCollection(seasons);
    }

}
