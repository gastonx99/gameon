package se.dandel.gameon.infrastructure.jpa;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.dandel.gameon.domain.model.Match;

public class JpaAbstractRepository {
    final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Inject
    private EntityManager entityManager;

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    public <T> void persist(T entity) {
        LOGGER.debug("Persisting {}", entity);
        entityManager.persist(entity);
    }

    public void refresh(Match entity) {
        getEntityManager().refresh(entity);
    }

    protected <T> Optional<T> getSingleResult(TypedQuery<T> query) {
        List<T> resultList = query.getResultList();
        if (resultList.size() > 1) {
            throw new NonUniqueResultException(
                    "Expected single result but got " + resultList.size() + " results from query " + query);
        } else {
            return resultList.isEmpty() ? Optional.empty() : Optional.of(resultList.get(0));
        }
    }

}
