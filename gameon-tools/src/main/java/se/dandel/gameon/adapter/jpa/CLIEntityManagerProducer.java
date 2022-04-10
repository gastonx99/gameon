package se.dandel.gameon.adapter.jpa;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.Specializes;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@Specializes
@ApplicationScoped
public class CLIEntityManagerProducer extends EntityManagerProducer {

    private static EntityManagerFactory factory;

    private static ThreadLocal<EntityManager> entityManager = new ThreadLocal<>();

    @Produces
    public EntityManager getEntityManager() {
        EntityManagerFactory factory = getOrCreateEntityManagerFactory();
        return getOrCreateEntityManager(factory);
    }

    private EntityManager getOrCreateEntityManager(EntityManagerFactory factory) {
        if (entityManager.get() == null) {
            entityManager.set(getOrCreateEntityManagerFactory().createEntityManager());
        }
        return entityManager.get();
    }

    private synchronized EntityManagerFactory getOrCreateEntityManagerFactory() {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory("GAMEON_STANDALONE");
        }
        return factory;
    }
}
