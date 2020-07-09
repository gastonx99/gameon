package se.dandel.gameon.infrastructure.jpa;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import se.dandel.gameon.domain.model.Game;

public class JpaAbstractRepository {

    @Inject
    private EntityManager entityManager;

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    public <T> void persist(T entity) {
        entityManager.persist(entity);
    }

    public void refresh(Game entity) {
        getEntityManager().refresh(entity);
    }
}
