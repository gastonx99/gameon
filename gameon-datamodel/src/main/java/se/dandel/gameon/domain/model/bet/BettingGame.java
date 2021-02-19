package se.dandel.gameon.domain.model.bet;

import se.dandel.gameon.domain.model.Season;
import se.dandel.gameon.domain.model.User;

import javax.persistence.*;
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
    private Collection<User> participants;

}
