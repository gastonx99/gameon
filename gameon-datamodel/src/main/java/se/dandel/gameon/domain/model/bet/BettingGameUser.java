package se.dandel.gameon.domain.model.bet;

import se.dandel.gameon.domain.model.Match;
import se.dandel.gameon.domain.model.Score;
import se.dandel.gameon.domain.model.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class BettingGameUser {

    public static enum BettingGameUserStatus {
        INVITED, JOINED, REJECTED, WITHDRAWN;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long pk;

    @ManyToOne
    private BettingGame bettingGame;

    @ManyToOne
    private User user;

    @OneToMany
    private Collection<Bet> bets = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    private BettingGameUserStatus status;

    protected BettingGameUser() {
        super();
    }

    public BettingGameUser(BettingGame bettingGame, User user) {
        this.bettingGame = bettingGame;
        this.user = user;
        this.status = BettingGameUserStatus.INVITED;
    }

    public long getPk() {
        return pk;
    }

    public void setPk(long pk) {
        this.pk = pk;
    }

    public BettingGame getBettingGame() {
        return bettingGame;
    }

    public User getUser() {
        return user;
    }

    public Collection<Bet> getBets() {
        return bets;
    }

    public BettingGameUserStatus getStatus() {
        return status;
    }

    public void setStatus(BettingGameUserStatus status) {
        this.status = status;
    }

    public void addBet(Match match, int homeScore, int awayScore) {
        Bet bet = new Bet(this, match);
        bet.setScore(new Score(homeScore, awayScore));
        this.bets.add(bet);
    }

}
