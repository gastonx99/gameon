package se.dandel.gameon.domain.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TOURNAMENT")
public class Tournament {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long pk;

    private String name;

    private Tournament() {
        // For JPA
    }

    public Tournament(TournamentType type) {
        this.type = type;
    }

    @Enumerated(EnumType.STRING)
    private TournamentType type;

    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Season> seasons = new ArrayList<>();

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

    void addSeason(Season season) {
        this.seasons.add(season);
    }

    public Collection<Season> getSeasons() {
        return Collections.unmodifiableCollection(seasons);
    }

}
