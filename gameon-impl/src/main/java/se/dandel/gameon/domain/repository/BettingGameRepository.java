package se.dandel.gameon.domain.repository;

import org.springframework.data.repository.ListCrudRepository;
import se.dandel.gameon.domain.model.bet.BettingGame;

public interface BettingGameRepository extends ListCrudRepository<BettingGame, Long> {

//    BettingGameUser findByParticipant(long pk);
}
