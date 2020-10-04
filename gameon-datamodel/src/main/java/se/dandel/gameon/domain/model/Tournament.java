package se.dandel.gameon.domain.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.*;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

@Entity
@Table(name = "TOURNAMENT")
public class Tournament {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long pk;

    private String name;

    private Locale.IsoCountryCode country;

    @Enumerated(EnumType.STRING)
    private TournamentType type;

    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Season> seasons = new ArrayList<>();

    private Tournament() {
        // For JPA
    }

    public Tournament(TournamentType type) {
        this.type = type;
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

    public Locale.IsoCountryCode getCountry() {
        return country;
    }

    public void setCountry(Locale.IsoCountryCode country) {
        this.country = country;
    }

    void addSeason(Season season) {
        this.seasons.add(season);
    }

    public Collection<Season> getSeasons() {
        return Collections.unmodifiableCollection(seasons);
    }

    public Optional<Season> getSeason(String name) {
        return seasons.stream().filter(season -> season.getName().equals(name)).findFirst();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, SHORT_PREFIX_STYLE).append("pk", pk).append("name", name).append("type", type)
                .append("seasons", seasons.size()).toString();
    }
}
