package se.dandel.gameon.adapter.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.Specializes;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@Specializes
@ApplicationScoped
public class ContainerTestEntityManagerProducer extends EntityManagerProducer {
    final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private static EntityManagerFactory factory;

    private static ThreadLocal<EntityManager> entityManager = new ThreadLocal<>();

    @Produces
    public EntityManager getEntityManager() {
        EntityManagerFactory factory = getOrCreateEntityManagerFactory();
        return getOrCreateEntityManager(factory);
    }

    private EntityManager getOrCreateEntityManager(EntityManagerFactory factory) {
        if (entityManager.get() == null) {
            EntityManager entityManager = factory.createEntityManager();
            LOGGER.info("Created entity manager {}", entityManager);
            ContainerTestEntityManagerProducer.entityManager.set(entityManager);
        }
        LOGGER.debug("Got entity manager {}", entityManager.get());
        return entityManager.get();
    }

    private synchronized EntityManagerFactory getOrCreateEntityManagerFactory() {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory("GAMEON_TEST");
        }
        return factory;
    }
}
