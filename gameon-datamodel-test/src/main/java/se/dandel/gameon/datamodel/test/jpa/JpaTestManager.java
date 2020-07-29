package se.dandel.gameon.datamodel.test.jpa;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JpaTestManager {
    private static final Logger LOG = LoggerFactory.getLogger(JpaTestManager.class);
    private final PersistenceContext persistenceContext;
    private String persistenceUnitName;
    private boolean deleteFromAllTables;
    private EntityManagerFactory factory;
    private EntityManager em;
    private EntityTransaction tx;
    private Connection connection;
    private DataSource dataSource = mock(DataSource.class);

    public JpaTestManager(PersistenceContext persistenceContext, String persistenceUnitName,
            boolean deleteFromAllTables) {
        this.persistenceContext = persistenceContext;
        this.persistenceUnitName = persistenceUnitName;
        this.deleteFromAllTables = deleteFromAllTables;
    }

    /**
     * Setup a EntityManagerFactory and injects jpatestmanager and entitymanager
     */
    public void begin() {
        startupFactory();
        createAndBegin();
    }

    public void after() {
        if (factory != null) {
            commitAndClose();
        }
        if (em != null) {
            em.close();
            em = null;
        }
    }

    public void reset() {
        commitAndClose();
        createAndBegin();
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public Connection getConnection() {
        return connection;
    }

    public DataSource getDataSource() throws SQLException {
        when(dataSource.getConnection()).thenReturn(connection);
        return dataSource;
    }

    /**
     * Reads properties for the factory being setup.
     */
    private void startupFactory() {
        Map<String, String> props = readPersistenceProperties();
        factory = Persistence.createEntityManagerFactory(persistenceUnitName, props);
    }

    public void clearDatabase() {
        new DbContentHandler().deleteFromAllTables(connection, deleteFromAllTables);
    }

    private void commitAndClose() {
        LOG.debug("commitAndClose");
        if (tx != null) {
            try {
                if (tx.isActive() && !tx.getRollbackOnly()) {
                    tx.commit();
                }
            } finally {
                tx = null;
            }
        }
        if (em != null) {
            //            try {
            //                em.close();
            //            } finally {
            //                em = null;
            //            }
            em.clear();
        }
    }

    /**
     * Setup an entitymanager and call annotation setups.
     */
    private void createAndBegin() {
        LOG.debug("createAndBegin");
        //        if (em != null || tx != null) {
        //            throw new IllegalStateException("Manager " + em + " and tx " + tx + " should all be null");
        //        }
        if (System.getProperty("oracle.net.tns_admin") == null && System.getenv("TNS_ADMIN") != null) {
            System.setProperty("oracle.net.tns_admin", System.getenv("TNS_ADMIN"));
        }
        if (em == null) {
            em = factory.createEntityManager();
        }
        tx = em.getTransaction();
        tx.begin();
        connection = em.unwrap(Connection.class);
        LOG.debug(toString());
    }

    private Map<String, String> readPersistenceProperties() {

        Map<String, String> props = new HashMap<>();
        if (persistenceContext != null && persistenceContext.properties() != null) {
            Arrays.stream(persistenceContext.properties()).forEach(p -> props.put(p.name(), p.value()));
        }

        //        props.putIfAbsent("javax.persistence.jdbc.url", "jdbc:hsqldb:file:C:/WS/database/hsqldb/gameon.db");
        //        props.putIfAbsent("javax.persistence.jdbc.user", "gameon");
        //        props.putIfAbsent("javax.persistence.jdbc.password", "gameon");
        return props;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("persistenceUnitName", persistenceUnitName).append("factory", factory)
                .append("em", em).append("tx", tx).append("connection", connection).toString();
    }

}
