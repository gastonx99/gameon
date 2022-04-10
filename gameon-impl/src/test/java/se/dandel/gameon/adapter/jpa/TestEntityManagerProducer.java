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
public class TestEntityManagerProducer extends EntityManagerProducer {
    final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private EntityManagerFactory factory;

    private EntityManager entityManager;

    @Produces
    public EntityManager getEntityManager() {
        if (entityManager == null) {
            factory = Persistence.createEntityManagerFactory("GAMEON_TEST");
            entityManager = factory.createEntityManager();
        }
        LOGGER.debug("Using entity manager {} and {} transaction {} from factory {}",
                entityManager,
                entityManager.getTransaction() != null && entityManager.getTransaction().isActive() ? "active" : "inactive",
                entityManager.getTransaction(),
                factory);
        return entityManager;
    }


}
