package se.dandel.gameon.adapter.jpa;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.Specializes;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
