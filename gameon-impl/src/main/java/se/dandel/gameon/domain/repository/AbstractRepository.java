package se.dandel.gameon.domain.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.dandel.gameon.domain.model.Match;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class AbstractRepository {
    final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Inject
    private EntityManager entityManager;

    protected EntityManager getEntityManager() {
        LOGGER.debug("Using entity manager {} and {} transaction {}",
                entityManager,
                entityManager.getTransaction() != null && entityManager.getTransaction().isActive() ? "active" : "inactive",
                entityManager.getTransaction());
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
