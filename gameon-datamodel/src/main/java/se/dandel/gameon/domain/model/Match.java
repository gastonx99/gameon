package se.dandel.gameon.domain.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import static java.util.Comparator.comparing;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

@Entity
@Table(name = "MATCH")
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

    private ZonedDateTime matchStart;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "home", column = @Column(name = "FINAL_SCORE_HOME")),
            @AttributeOverride(name = "away", column = @Column(name = "FINAL_SCORE_AWAY"))})
    private Score finalScore;

    public Match() {
    }

    public Match(Season season) {
        this.season = season;
        season.addMatch(this);
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

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setTeams(Team homeTeam, Team awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public void setMatchStart(ZonedDateTime matchStart) {
        this.matchStart = matchStart;
    }

    public ZonedDateTime getMatchStart() {
        return matchStart;
    }

    public Season getSeason() {
        return season;
    }

    public Score getFinalScore() {
        return finalScore;
    }

    public boolean isSame(ZonedDateTime zonedDateTime, Team homeTeam, Team awayTeam) {
        return new EqualsBuilder().append(this.matchStart, zonedDateTime)
                .append(this.homeTeam.getKey(), homeTeam.getKey()).append(this.awayTeam.getKey(), awayTeam.getKey())
                .build().booleanValue();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, SHORT_PREFIX_STYLE).append("pk", pk)
                .append("season", season == null ? "" : season.getName()).append("venue", venue)
                .append("homeTeam", homeTeam).append("awayTeam", awayTeam).append("zonedDateTime", matchStart)
                .toString();
    }

    public void setFinalScore(int home, int away) {
        finalScore = new Score(home, away);
    }
}
