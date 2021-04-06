package se.dandel.gameon.domain.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import static java.util.Comparator.comparing;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

@Entity
@Table(name = "FIXTURE")
public class Match {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long pk;

    @ManyToOne
    private Season season;

    @ManyToOne
    private Venue venue;

    @ManyToOne
    private Team homeTeam;

    @ManyToOne
    private Team awayTeam;

    private LocalDateTime matchStart;

    @Enumerated(EnumType.STRING)
    private MatchStatus status;

    private String statustext;

    private String stage;

    @Column(name = "GROUP_TEXT")
    private String group;

    private String round;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "home", column = @Column(name = "FINAL_SCORE_HOME")),
            @AttributeOverride(name = "away", column = @Column(name = "FINAL_SCORE_AWAY"))})
    private Score finalScore;

    @Embedded
    private RemoteKey remoteKey;

    public Match() {
    }

    public static Set<Team> getDistinctTeams(Collection<Match> matches) {
        Set<Team> c = new TreeSet<>(comparing(Team::getName));
        matches.forEach(m -> {
            c.add(m.getHomeTeam());
            c.add(m.getAwayTeam());
        });
        return c;
    }

    public long getPk() {
        return pk;
    }

    public void setPk(long pk) {
        this.pk = pk;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public void setMatchStart(LocalDateTime matchStart) {
        this.matchStart = matchStart;
    }

    public LocalDateTime getMatchStart() {
        return matchStart;
    }

    public MatchStatus getStatus() {
        return status;
    }

    public void setStatus(MatchStatus status) {
        this.status = status;
    }

    public String getStatustext() {
        return statustext;
    }

    public void setStatustext(String statusText) {
        this.statustext = statusText;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public Score getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(Score finalScore) {
        this.finalScore = finalScore;
    }

    public RemoteKey getRemoteKey() {
        return remoteKey;
    }

    public void setRemoteKey(RemoteKey remoteKey) {
        this.remoteKey = remoteKey;
    }

    public boolean isSame(LocalDateTime matchStart, Team homeTeam, Team awayTeam) {
        return new EqualsBuilder().append(this.matchStart, matchStart)
                .append(this.homeTeam.getName(), homeTeam.getName()).append(this.awayTeam.getName(), awayTeam.getName())
                .build().booleanValue();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, SHORT_PREFIX_STYLE).append("pk", pk)
                .append("season", season == null ? "" : season.getName()).append("venue", venue)
                .append("homeTeam", homeTeam).append("awayTeam", awayTeam).append("matchStart", matchStart)
                .toString();
    }

}
