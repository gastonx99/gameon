package se.dandel.gameon.domain.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import se.dandel.gameon.domain.model.RemoteKey;
import se.dandel.gameon.domain.model.Team;

import java.util.Optional;

@Repository
public interface TeamRepository extends ListCrudRepository<Team, Long> {

    Optional<Team> findByRemoteKey(RemoteKey remoteKey);
}
