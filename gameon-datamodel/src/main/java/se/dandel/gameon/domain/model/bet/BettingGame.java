package se.dandel.gameon.domain.model.bet;

import jakarta.persistence.*;
import se.dandel.gameon.domain.model.Audit;
import se.dandel.gameon.domain.model.Season;
import se.dandel.gameon.domain.model.User;

import java.util.ArrayList;
import java.util.Collection;

@Entity
public class BettingGame {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long pk;

    private String name;

    @ManyToOne
    private Season season;

    @ManyToOne
    private User owner;

    @ManyToMany
    private Collection<BettingGameUser> participants = new ArrayList<>();

    @Version
    private long version;

    @Embedded
    private Audit audit = new Audit();

    public BettingGame() {
        super();
    }

    public long getPk() {
        return pk;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Collection<BettingGameUser> getParticipants() {
        return participants;
    }

    public void addParticipant(BettingGameUser participant) {
        this.participants.add(participant);
    }
}
