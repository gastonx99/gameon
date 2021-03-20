package se.dandel.gameon.adapter.out.rest.api2;

import org.apache.commons.lang3.builder.RecursiveToStringStyle;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class GameDTO {
    private String awayTeamCode;

    private int awayTeamResult;

    private boolean gameCenterActive;

    private int gameId;

    private String gameType;

    private String gameUuid;

    private boolean highlightsCoverageEnabled;

    private String homeTeamCode;

    private int homeTeamResult;

    private boolean liveCoverageEnabled;

    private boolean overtime;

    private boolean penaltyShots;

    private boolean played;

    private String season;

    private String series;

    private String startDateTime;

    private String[] tvChannels;

    private String venue;

    public String getAwayTeamCode() {
        return awayTeamCode;
    }

    public void setAwayTeamCode(String awayTeamCode) {
        this.awayTeamCode = awayTeamCode;
    }

    public int getAwayTeamResult() {
        return awayTeamResult;
    }

    public void setAwayTeamResult(int awayTeamResult) {
        this.awayTeamResult = awayTeamResult;
    }

    public boolean isGameCenterActive() {
        return gameCenterActive;
    }

    public void setGameCenterActive(boolean gameCenterActive) {
        this.gameCenterActive = gameCenterActive;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getGameUuid() {
        return gameUuid;
    }

    public void setGameUuid(String gameUuid) {
        this.gameUuid = gameUuid;
    }

    public boolean isHighlightsCoverageEnabled() {
        return highlightsCoverageEnabled;
    }

    public void setHighlightsCoverageEnabled(boolean highlightsCoverageEnabled) {
        this.highlightsCoverageEnabled = highlightsCoverageEnabled;
    }

    public String getHomeTeamCode() {
        return homeTeamCode;
    }

    public void setHomeTeamCode(String homeTeamCode) {
        this.homeTeamCode = homeTeamCode;
    }

    public int getHomeTeamResult() {
        return homeTeamResult;
    }

    public void setHomeTeamResult(int homeTeamResult) {
        this.homeTeamResult = homeTeamResult;
    }

    public boolean isLiveCoverageEnabled() {
        return liveCoverageEnabled;
    }

    public void setLiveCoverageEnabled(boolean liveCoverageEnabled) {
        this.liveCoverageEnabled = liveCoverageEnabled;
    }

    public boolean isOvertime() {
        return overtime;
    }

    public void setOvertime(boolean overtime) {
        this.overtime = overtime;
    }

    public boolean isPenaltyShots() {
        return penaltyShots;
    }

    public void setPenaltyShots(boolean penaltyShots) {
        this.penaltyShots = penaltyShots;
    }

    public boolean isPlayed() {
        return played;
    }

    public void setPlayed(boolean played) {
        this.played = played;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String[] getTvChannels() {
        return tvChannels;
    }

    public void setTvChannels(String[] tvChannels) {
        this.tvChannels = tvChannels;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, RecursiveToStringStyle.SHORT_PREFIX_STYLE);
    }
}
