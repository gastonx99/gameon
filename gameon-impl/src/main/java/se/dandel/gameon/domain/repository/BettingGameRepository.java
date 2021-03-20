package se.dandel.gameon.domain.repository;

import se.dandel.gameon.domain.model.bet.BettingGame;
import se.dandel.gameon.domain.model.bet.BettingGameUser;
import se.dandel.gameon.domain.repository.AbstractRepository;

import javax.persistence.TypedQuery;
import java.util.Collection;

public class BettingGameRepository extends AbstractRepository {

    public Collection<BettingGame> findAll() {
        TypedQuery<BettingGame> query = getEntityManager().createQuery("select bg from BettingGame bg", BettingGame.class);
        return query.getResultList();
    }

    public BettingGame getBettingGame(long pk) {
        return getEntityManager().find(BettingGame.class, pk);
    }

    public BettingGameUser getBettingGameUser(long pk) {
        return getEntityManager().find(BettingGameUser.class, pk);
    }
}
