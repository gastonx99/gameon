package se.dandel.gameon.domain.repository;

import org.springframework.data.repository.ListCrudRepository;
import se.dandel.gameon.domain.model.Match;
import se.dandel.gameon.domain.model.RemoteKey;

import java.util.Optional;

public interface MatchRepository extends ListCrudRepository<Match, Long> {

    Optional<Match> findByRemoteKey(RemoteKey remoteKey);

}
