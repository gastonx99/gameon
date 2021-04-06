package se.dandel.gameon.domain.repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
