package se.dandel.gameon.domain.repository;

import se.dandel.gameon.domain.model.Country;

import javax.persistence.TypedQuery;
import java.util.Optional;

public class CountryRepository extends AbstractRepository {
    public Optional<Country> find(Country country) {
        TypedQuery<Country> query =
                getEntityManager().createQuery("select c from Country c where c.remoteKey = :remoteKey", Country.class);
        query.setParameter("remoteKey", country.getRemoteKey());
        return getSingleResult(query);
    }

    public Optional<Country> findByCountryCode(String countryCode) {
        TypedQuery<Country> query =
                getEntityManager().createQuery("select c from Country c where c.countryCode = :countryCode", Country.class);
        query.setParameter("countryCode", countryCode);
        return getSingleResult(query);
    }
}
