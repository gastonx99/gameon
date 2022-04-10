package se.dandel.gameon.domain.model.bet;

import se.dandel.gameon.domain.model.Audit;
import se.dandel.gameon.domain.model.Match;
import se.dandel.gameon.domain.model.Score;

import jakarta.persistence.*;

@Entity
public class Bet {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long pk;

    @ManyToOne
    private BettingGameUser bettingGameUser;

    @ManyToOne
    private Match match;

    @Embedded
    private Score score;

    @Version
    private long version;

    @Embedded
    private Audit audit = new Audit();

    protected Bet() {
        super();
    }

    public Bet(BettingGameUser bettingGameUser, Match match) {
        this.bettingGameUser = bettingGameUser;
        this.match = match;
    }

    public long getPk() {
        return pk;
    }

    public void setPk(long pk) {
        this.pk = pk;
    }

    public BettingGameUser getBettingGameUser() {
        return bettingGameUser;
    }

    public Match getMatch() {
        return match;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }
}
