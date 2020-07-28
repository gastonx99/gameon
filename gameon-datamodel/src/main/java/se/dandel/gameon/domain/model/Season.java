package se.dandel.gameon.domain.model;

import static java.util.Comparator.comparing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
        Set<Team> c = new TreeSet<>(comparing(Team::getName));
        matches.forEach(m -> {
            c.add(m.getHomeTeam());
            c.add(m.getAwayTeam());
        });
        return c;
    }
}
