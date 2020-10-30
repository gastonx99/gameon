package se.dandel.gameon.datamodel.test.jpa;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.sql.Connection;

public class PersistenceTestManager {
    final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Inject
    private EntityManager entityManager;

    @PostConstruct
    public void postConstruct() {
        LOGGER.debug("PostConstruct, with entity manager {}", entityManager);
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        Connection connection = entityManager.unwrap(Connection.class);
        Validate.notNull(connection, "Unwrapping connection from entity manager returns null, maybe no transaction exists?");

        String liquibaseChangelog = "liquibase/master-test.xml";
        DbContentHandler dbContentHandler = new DbContentHandler();
        dbContentHandler.createTablesIfNotExists(connection, liquibaseChangelog);
        dbContentHandler.deleteFromAllTables(connection);
        entityManager.getTransaction().commit();
        entityManager.clear();
        entityManager.getTransaction().begin();
    }

    @PreDestroy
    public void preDestroy() {
        LOGGER.debug("Predestroy, with entity manager {}", entityManager);
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().commit();
        }
        entityManager.clear();
    }


    public EntityManager em() {
        return entityManager;
    }
}
