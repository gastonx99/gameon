package se.dandel.gameon.domain.repository;

import org.springframework.data.repository.ListCrudRepository;
import se.dandel.gameon.domain.model.RemoteKey;
import se.dandel.gameon.domain.model.Tournament;

import java.util.Optional;

public interface TournamentRepository extends ListCrudRepository<Tournament, Long> {

    Optional<Tournament> findByRemoteKey(RemoteKey remoteKey);

}
