package se.dandel.gameon.infrastructure.jpa;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class EntityManagerProducer {

    @PersistenceContext(unitName = "GAMEON")
    private EntityManager em;

    @Produces
    public EntityManager getEntityManager() {
        return em;
    }

}
