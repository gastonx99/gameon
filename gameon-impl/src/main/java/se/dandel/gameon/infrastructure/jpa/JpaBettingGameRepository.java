package se.dandel.gameon.infrastructure.jpa;

import se.dandel.gameon.domain.model.bet.BettingGame;
import se.dandel.gameon.domain.model.bet.BettingGameUser;

import javax.persistence.TypedQuery;
import java.util.Collection;

public class JpaBettingGameRepository extends JpaAbstractRepository {

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
