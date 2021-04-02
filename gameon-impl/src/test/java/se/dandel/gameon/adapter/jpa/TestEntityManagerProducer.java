package se.dandel.gameon.adapter.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.Specializes;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
