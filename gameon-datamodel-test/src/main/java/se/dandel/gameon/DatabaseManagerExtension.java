package se.dandel.gameon;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.*;
import javax.persistence.EntityManager;
import java.sql.Connection;

public class DatabaseManagerExtension implements Extension {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private static final String LIQUIBASE_CHANGELOG = "liquibase/master-test.xml";

    private static boolean databaseInitalized;

    private EntityManager entityManager;

    public void observePAT(@Observes AfterDeploymentValidation afterDeploymentValidation, BeanManager beanManager) {
        initialize(beanManager);
        Connection connection = getConnection();
        DbContentHandler dbContentHandler = new DbContentHandler();
        if (!databaseInitalized) {
            databaseInitalized = true;
            LOGGER.debug("Running Liquibase scripts");
            dbContentHandler.createTablesIfNotExists(connection, LIQUIBASE_CHANGELOG);
            LOGGER.debug("Liquibase initialization done");
        }
        dbContentHandler.deleteFromAllTables(connection);
    }

    private void initialize(BeanManager beanManager) {
        Bean<EntityManager> bean = (Bean<EntityManager>) beanManager.resolve(beanManager.getBeans(EntityManager.class));
        entityManager = (EntityManager) beanManager.getReference(bean, EntityManager.class, beanManager.createCreationalContext(bean));
    }

    public void afterEach(@Observes BeforeShutdown beforeShutdown) {
        LOGGER.debug("After each test");
        EntityManager entityManager = getEntityManager();
        if (entityManager.getTransaction().isActive()) {
            entityManager.flush();
            if (!entityManager.getTransaction().getRollbackOnly()) {
                entityManager.getTransaction().commit();
            }
        }
    }

    private Connection getConnection() {
        EntityManager entityManager = getEntityManager();
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        Connection connection = entityManager.unwrap(Connection.class);
        Validate.notNull(connection, "Unwrapping connection from entity manager returns null, maybe no transaction exists?");
        return connection;
    }

    private EntityManager getEntityManager() {
        LOGGER.debug("Using entity manager {} and {} transaction {}",
                entityManager,
                entityManager.getTransaction() != null && entityManager.getTransaction().isActive() ? "active" : "inactive",
                entityManager.getTransaction());
        return entityManager;
    }

}
