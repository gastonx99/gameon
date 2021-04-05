package se.dandel.gameon.datamodel.test.jpa;

import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DbContentHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private static final String DATABASE_ALREADY_CREATED_TABLENAME = "DATABASE_ALREADY_CREATED_TABLE";

    private static final String DATABASE_ALREADY_CREATED_TABLE_SQL =
            "create table " + DATABASE_ALREADY_CREATED_TABLENAME + " (a integer)";

    private static final Set<String> DELETE_EXCLUDES_TABLE = new HashSet<>();

    static {
        // Liquibasetabeller
        DELETE_EXCLUDES_TABLE.add("DATABASECHANGELOGLOCK");
        DELETE_EXCLUDES_TABLE.add(DATABASE_ALREADY_CREATED_TABLENAME);
    }

    private List<String> deleteFromTableStatements;

    /**
     * Delete alla tabled except tables listed in {@code DELETE_EXCLUDES_TABLE}
     */
    public void deleteFromAllTables(Connection connection) {
        LOGGER.info("Deleting from all tables");
        try (java.sql.Statement statement = connection.createStatement()) {
            deleteFromTables(connection, statement, getDeleteFromTableStatements(connection), 0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        LOGGER.info("Deleted from all tables");
    }

    private void deleteFromTables(Connection connection, java.sql.Statement statement, List<String> stmts,
                                  int startIndex) {
        LOGGER.debug("Deleting from tables");
        int numExecuted = startIndex;
        try {
            for (String str : stmts) {
                LOGGER.debug("Executing {}", str);
                statement.execute(str);
                connection.commit();
                numExecuted++;
            }
        } catch (Exception e) {
            List<String> remaining = stmts.subList(numExecuted, stmts.size());
            String bad = remaining.remove(0);
            remaining.add(bad);

            List<String> newStmts = new ArrayList<>();
            newStmts.addAll(stmts.subList(0, numExecuted));
            newStmts.addAll(remaining);

            deleteFromTableStatements = newStmts;
            if (deleteFromTableStatements.size() != stmts.size()) {
                throw new IllegalStateException("ooooh this is bad");
            }
            deleteFromTables(connection, statement, stmts, startIndex);
        }
    }

    private List<String> getDeleteFromTableStatements(Connection connection) {
        deleteFromTableStatements = new ArrayList<>();
        try (ResultSet tables = connection.getMetaData().getTables(null, null, null, null)) {
            while (tables.next()) {
                String tableType = tables.getString("TABLE_TYPE");
                if ("TABLE".equals(tableType)) {
                    String tablename = tables.getString("TABLE_NAME");
                    if (isOkToDeleteFrom(tablename)) {
                        String stmt = "delete from " + tablename;
                        deleteFromTableStatements.add(stmt);
                        LOGGER.debug("Preparing delete statement: {}", stmt);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return deleteFromTableStatements;
    }

    private boolean isOkToDeleteFrom(String tablename) {
        return !DELETE_EXCLUDES_TABLE.contains(tablename);
    }

    public void createTablesIfNotExists(Connection connection, String liquibaseChangelog) {
        if (!isDatabaseAlreadyCreated(connection)) {
            createDatabase(liquibaseChangelog, connection);
            createDatabaseAlreadyCreatedTable(connection);
        }
    }

    private void createDatabase(String liquibaseChangelog, Connection connection) {
        LOGGER.info("Creating database using liquibaseChangelog: {}", liquibaseChangelog);
        loadLiquibaseChangelog(connection, liquibaseChangelog);
    }

    protected boolean isDatabaseAlreadyCreated(Connection connection) {
        try (ResultSet tables = connection.getMetaData()
                .getTables(null, null, DATABASE_ALREADY_CREATED_TABLENAME, null)) {
            LOGGER.debug("Fetching metadata to check if database is already created");
            LOGGER.debug("Fetched metadata");
            return tables.next();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private void loadLiquibaseChangelog(Connection connection, String liquibaseChangelog) {
        try {
            ClassLoaderResourceAccessor resourceAccessor =
                    new ClassLoaderResourceAccessor(getClass().getClassLoader());

            JdbcConnection jdbcConnection = new JdbcConnection(connection);
            Liquibase liquibase = new Liquibase(liquibaseChangelog, resourceAccessor, jdbcConnection);
            liquibase.update((String) null);
        } catch (LiquibaseException liquibaseException) {
            throw new RuntimeException(liquibaseException);
        }
    }

    protected void createDatabaseAlreadyCreatedTable(Connection connection) {
        try (java.sql.Statement statement = connection.createStatement()) {
            statement.execute(DATABASE_ALREADY_CREATED_TABLE_SQL);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
