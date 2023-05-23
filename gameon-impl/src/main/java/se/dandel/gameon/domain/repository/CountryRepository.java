package se.dandel.gameon.domain.repository;

import org.springframework.data.repository.ListCrudRepository;
import se.dandel.gameon.domain.model.Country;
import se.dandel.gameon.domain.model.RemoteKey;

import java.util.Optional;

public interface CountryRepository extends ListCrudRepository<Country, Long> {
    Optional<Country> findByRemoteKey(RemoteKey remoteKey);
}
