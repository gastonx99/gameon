package se.dandel.gameon.domain.model.bet;

import se.dandel.gameon.domain.model.Audit;
import se.dandel.gameon.domain.model.Season;
import se.dandel.gameon.domain.model.User;

import javax.persistence.*;
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

    protected BettingGame() {
        super();
    }

    public BettingGame(Season season, User owner, String name) {
        this.season = season;
        this.owner = owner;
        this.name = name;
    }

    public long getPk() {
        return pk;
    }

    public String getName() {
        return name;
    }

    public Season getSeason() {
        return season;
    }

    public User getOwner() {
        return owner;
    }

    public Collection<BettingGameUser> getParticipants() {
        return participants;
    }

    public void addParticipant(BettingGameUser participant) {
        this.participants.add(participant);
    }
}
