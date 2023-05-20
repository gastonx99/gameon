package se.dandel.gameon.domain.repository;

import org.springframework.data.repository.ListCrudRepository;
import se.dandel.gameon.domain.model.bet.BettingGame;
import se.dandel.gameon.domain.model.bet.BettingGameUser;

public interface BettingGameRepository extends ListCrudRepository<BettingGame, Long> {

    BettingGame findByPrimaryKey(long pk);

    BettingGameUser findByBettingGameUser(long pk);
}
