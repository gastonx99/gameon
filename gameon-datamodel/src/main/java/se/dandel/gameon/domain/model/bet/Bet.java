package se.dandel.gameon.domain.model.bet;

import se.dandel.gameon.domain.model.Match;
import se.dandel.gameon.domain.model.Score;
import se.dandel.gameon.domain.model.User;

import javax.persistence.*;

@Entity
public class Bet {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long pk;

    @OneToOne
    private User user;

    @OneToOne
    private BettingGame bettingGame;

    @OneToOne
    private Match match;

    @Embedded
    private Score score = new Score();

    public long getPk() {
        return pk;
    }

    public void setPk(long pk) {
        this.pk = pk;
    }

}
