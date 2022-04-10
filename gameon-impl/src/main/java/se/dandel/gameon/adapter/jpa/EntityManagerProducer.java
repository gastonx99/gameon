package se.dandel.gameon.adapter.jpa;

import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class EntityManagerProducer {

    @PersistenceContext(unitName = "GAMEON")
    private EntityManager em;

    @Produces
    public EntityManager getEntityManager() {
        return em;
    }

}
