package se.dandel.gameon.domain.model;

import jakarta.persistence.*;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

@Entity
public class Season {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long pk;

    @ManyToOne
    private Tournament tournament;

    private String name;

    private SeasonStatus status;

    @OneToMany(mappedBy = "season", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Match> matches = new ArrayList<>();

    @Column(name = "START_DATE")
    private LocalDate startDate;

    @Column(name = "END_DATE")
    private LocalDate endDate;

    @Embedded
    private RemoteKey remoteKey;

    @Version
    private long version;

    @Embedded
    private Audit audit = new Audit();

    public Season() {
        this.status = SeasonStatus.NOT_STARTED;
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

    public SeasonStatus getStatus() {
        return status;
    }

    public void setStatus(SeasonStatus status) {
        this.status = status;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public RemoteKey getRemoteKey() {
        return remoteKey;
    }

    public void setRemoteKey(RemoteKey remoteKey) {
        this.remoteKey = remoteKey;
    }

    public void addMatch(Match match) {
        this.matches.add(match);
    }

    public Collection<Match> getMatches() {
        return Collections.unmodifiableCollection(matches);
    }

    public Optional<Match> getMatch(LocalDateTime matchStart, Team homeTeam, Team awayTeam) {
        return matches.stream().filter(match -> match.isSame(matchStart, homeTeam, awayTeam)).findFirst();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, SHORT_PREFIX_STYLE).append("pk", pk)
                .append("tournament", tournament == null ? "" : tournament.getName()).append("name", name)
                .append("matches", matches.size()).toString();
    }
}
