package se.dandel.gameon.domain.model;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name = "SEASON")
public class Season {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long pk;

    @ManyToOne
    private Tournament tournament;

    private String name;

    @OneToMany(mappedBy = "season", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Match> matches = new ArrayList<>();

    private Season() {
        // For JPA
    }

    public Season(Tournament tournament) {
        this.tournament = tournament;
        tournament.addSeason(this);
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

    void addMatch(Match match) {
        this.matches.add(match);
    }

    public Collection<Match> getMatches() {
        return Collections.unmodifiableCollection(matches);
    }

    public Set<Team> getTeams() {
        return Match.getDistinctTeams(matches);
    }

    public Optional<Match> getMatch(ZonedDateTime zonedDateTime, Team homeTeam, Team awayTeam) {
        return matches.stream().filter(match -> match.isSame(zonedDateTime, homeTeam, awayTeam)).findFirst();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, SHORT_PREFIX_STYLE).append("pk", pk)
                .append("tournament", tournament == null ? "" : tournament.getName()).append("name", name)
                .append("matches", matches.size()).toString();
    }
}
