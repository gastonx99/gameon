package se.dandel.gameon.domain.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

@Entity
public class Tournament {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long pk;

    private String name;

    @ManyToOne
    private Country country;

    @Enumerated(EnumType.STRING)
    private TournamentType type;

    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Season> seasons = new ArrayList<>();

    @Embedded
    private RemoteKey remoteKey;

    @Version
    private long version;

    @Embedded
    private Audit audit = new Audit();

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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public RemoteKey getRemoteKey() {
        return remoteKey;
    }

    public void setRemoteKey(RemoteKey remoteKey) {
        this.remoteKey = remoteKey;
    }

    public void addSeason(Season season) {
        this.seasons.add(season);
    }

    public Collection<Season> getSeasons() {
        return Collections.unmodifiableCollection(seasons);
    }

    public Optional<Season> findSeason(String name) {
        return seasons.stream().filter(season -> season.getName().equals(name)).findFirst();
    }

    public Season getSeason(String name) {
        return seasons.stream().filter(season -> season.getName().equals(name)).findFirst().orElseThrow();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, SHORT_PREFIX_STYLE).append("pk", pk).append("name", name).append("type", type)
                .append("seasons", seasons.size()).toString();
    }
}
