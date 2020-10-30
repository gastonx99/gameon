package se.dandel.gameon.test.container;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.dandel.gameon.infrastructure.jpa.EntityManagerProducer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.Specializes;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
            entityManager.set(factory.createEntityManager());
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
