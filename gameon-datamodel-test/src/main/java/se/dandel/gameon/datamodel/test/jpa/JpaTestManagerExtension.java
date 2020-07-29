package se.dandel.gameon.datamodel.test.jpa;

import java.sql.Connection;
import java.sql.ResultSet;

import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

@JpaTestConfig(persistenceUnitName = "GAMEON_TEST", liquibaseChangelog = "liquibase/master-test.xml")
public class JpaTestManagerExtension implements ParameterResolver, BeforeEachCallback, AfterEachCallback {
    private static final Logger LOG = LoggerFactory.getLogger(JpaTestManagerExtension.class);

    private static final String DATABASE_ALREADY_CREATED_TABLENAME = "DATABASE_ALREADY_CREATED_TABLE";
    private static final String DATABASE_ALREADY_CREATED_TABLE_SQL =
            "create table " + DATABASE_ALREADY_CREATED_TABLENAME + " (a integer)";

    /**
     * Runs each time before a test is executed.
     * Creates a jpaTestManager that is saved in a extensionContext as a map.
     * Injects annotation(s) and loads the database in jpaTestManager
     */
    @Override
    public void beforeEach(ExtensionContext context) {
        LOG.debug("Root context: {}", context.getRoot().getDisplayName());
        LOG.debug("Context: {}", context.getDisplayName());
        JpaTestConfig config = this.getClass().getAnnotation(JpaTestConfig.class);
        JpaTestManager jpaTestManager = initJpaTestManager(context, config);
        jpaTestManager.begin();
        initDatabase(context, config.liquibaseChangelog(), jpaTestManager.getConnection(), config.createDatababase());
        jpaTestManager.reset();
        storeJpaTestManager(context, jpaTestManager);
    }

    @Override
    public void afterEach(ExtensionContext context) {
        getJpaTestManager(context).after();
        getJpaTestManager(context).clearDatabase();
        removeJpaTestManager(context, JpaTestManager.class.getName());
    }

    private void storeJpaTestManager(ExtensionContext context, JpaTestManager jpaTestManager) {
        context.getStore(ExtensionContext.Namespace.GLOBAL).put(JpaTestManager.class.getName(), jpaTestManager);
    }

    private JpaTestManager initJpaTestManager(ExtensionContext context, JpaTestConfig config) {
        PersistenceContext persistenceContext = context.getRequiredTestClass().getAnnotation(PersistenceContext.class);
        return new JpaTestManager(persistenceContext, config.persistenceUnitName(), config.deleteFromAllTables());
    }

    private JpaTestManager getJpaTestManager(ExtensionContext context) {
        return (JpaTestManager) context.getStore(ExtensionContext.Namespace.GLOBAL).get(JpaTestManager.class.getName());
    }

    private void initDatabase(ExtensionContext context, String liquibaseChangelog, Connection connection,
            boolean createDatabase) {
        if (createDatabase && !isDatabaseAlreadyCreated(connection)) {
            createDatabase(context, liquibaseChangelog, connection);
            createDatabaseAlreadyCreatedTable(connection);
        }
    }

    /**
     * Helper method load the overall database.
     */
    private void createDatabase(ExtensionContext context, String liquibaseChangelog, Connection connection) {
        LOG.debug("Database - liquibaseChangelog: {}", liquibaseChangelog);
        loadLiquibaseChangelog(context, connection, liquibaseChangelog);
    }

    private void loadLiquibaseChangelog(ExtensionContext context, Connection connection, String liquibaseChangelog) {
        try {
            ClassLoaderResourceAccessor resourceAccessor =
                    new ClassLoaderResourceAccessor(context.getRequiredTestClass().getClassLoader());

            JdbcConnection jdbcConnection = new JdbcConnection(connection);
            Liquibase liquibase = new Liquibase(liquibaseChangelog, resourceAccessor, jdbcConnection);
            liquibase.update((String) null);
        } catch (LiquibaseException liquibaseException) {
            throw new RuntimeException(liquibaseException);
        }
    }

    private void removeJpaTestManager(ExtensionContext context, String key) {
        context.getStore(ExtensionContext.Namespace.GLOBAL).remove(key);
    }

    protected void createDatabaseAlreadyCreatedTable(Connection connection) {
        try (java.sql.Statement statement = connection.createStatement()) {
            statement.execute(DATABASE_ALREADY_CREATED_TABLE_SQL);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected boolean isDatabaseAlreadyCreated(Connection connection) {
        try (ResultSet tables = connection.getMetaData()
                .getTables(null, null, DATABASE_ALREADY_CREATED_TABLENAME, null)) {
            LOG.debug("Fetching metadata to check if database is already created");
            LOG.debug("Fetched metadata");
            return tables.next();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(JpaTestManager.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {

        return parameterContext.getParameter().getType().equals(JpaTestManager.class) ?
                getJpaTestManager(extensionContext) :
                null;
    }
}
