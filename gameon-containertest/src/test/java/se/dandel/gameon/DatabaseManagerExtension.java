package se.dandel.gameon;

import org.apache.commons.lang3.Validate;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import se.dandel.gameon.datamodel.test.jpa.DbContentHandler;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.persistence.EntityManager;
import java.sql.Connection;

public class DatabaseManagerExtension implements Extension, BeforeEachCallback, AfterEachCallback {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private static final String LIQUIBASE_CHANGELOG = "liquibase/master-test.xml";

    static {
        SLF4JBridgeHandler.install();
    }

    private BeanManager beanManager;

    public void observePAT(@Observes AfterDeploymentValidation afterDeploymentValidation, BeanManager beanManager) {
        LOGGER.debug("Running Liquibase scripts");
        this.beanManager = beanManager;
        EntityManager entityManager = getEntityManager();
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        Connection connection = entityManager.unwrap(Connection.class);
        Validate.notNull(connection, "Unwrapping connection from entity manager returns null, maybe no transaction exists?");

        DbContentHandler dbContentHandler = new DbContentHandler();
        dbContentHandler.createTablesIfNotExists(connection, LIQUIBASE_CHANGELOG);
        LOGGER.debug("Liquibase initialization done");
    }

    private EntityManager getEntityManager() {
        Bean<EntityManager> bean = (Bean<EntityManager>) beanManager.resolve(beanManager.getBeans(EntityManager.class));
        return (EntityManager) beanManager.getReference(bean, EntityManager.class, beanManager.createCreationalContext(bean));
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        EntityManager entityManager = getEntityManager();
        LOGGER.debug("Before each test, with entity manager {}", entityManager);
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        Connection connection = entityManager.unwrap(Connection.class);
        Validate.notNull(connection, "Unwrapping connection from entity manager returns null, maybe no transaction exists?");

        DbContentHandler dbContentHandler = new DbContentHandler();
        dbContentHandler.deleteFromAllTables(connection);
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
        EntityManager entityManager = getEntityManager();
        LOGGER.debug("After each test, with entity manager {}", entityManager);
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().commit();
        }
        entityManager.clear();
    }
}
