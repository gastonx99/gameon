package se.dandel.gameon.domain.repository;

import org.springframework.data.repository.ListCrudRepository;
import se.dandel.gameon.domain.model.Season;
import se.dandel.gameon.domain.model.Tournament;

import java.util.Optional;

public interface SeasonRepository extends ListCrudRepository<Season, Long> {

    Optional<Season> findByTournament(Tournament tournament);

}
