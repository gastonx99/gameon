package se.dandel.gameon.domain.repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Collection;

public class AllPurposeTestRepository extends AbstractRepository {
    @Inject
    EntityManager entityManager;

    public <T> Collection<T> findAll(Class<T> clazz) {
        return entityManager.createQuery(entityManager.getCriteriaBuilder().createQuery(clazz)).getResultList();
    }
}
