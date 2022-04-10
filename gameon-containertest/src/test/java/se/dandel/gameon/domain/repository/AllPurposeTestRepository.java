package se.dandel.gameon.domain.repository;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.Collection;

public class AllPurposeTestRepository extends AbstractRepository {
    @Inject
    EntityManager entityManager;

    public <T> Collection<T> findAll(Class<T> clazz) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(clazz);
        Root<T> variableRoot = query.from(clazz);
        query.select(variableRoot);
        return entityManager.createQuery(query).getResultList();
    }
}
