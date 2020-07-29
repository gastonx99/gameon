package se.dandel.gameon.rest;

public class MatchModel {
    private String hometeam;
    private String awayTeam;
    private String dateTime;
    private String venue;

    public void setHomeTeam(String hometeam) {
        this.hometeam = hometeam;
    }

    public String getHometeam() {
        return hometeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }
}
