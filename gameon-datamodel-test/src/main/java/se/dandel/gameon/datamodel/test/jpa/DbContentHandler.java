package se.dandel.gameon.datamodel.test.jpa;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbContentHandler {
    private static final Logger LOG = LoggerFactory.getLogger(DbContentHandler.class);
    private static final Set<String> DELETE_EXCLUDES_TABLE = new HashSet<>();

    static {
        // Liquibasetabeller
        DELETE_EXCLUDES_TABLE.add("DATABASECHANGELOGLOCK");
        DELETE_EXCLUDES_TABLE.add("DATABASE_ALREADY_CREATED_TABLE");

        // Basdatatabeller
        //        DELETE_EXCLUDES_TABLE.add("T_IN_ADRESSREGISTER");
    }

    private List<String> deleteFromTableStatements;
    private boolean deleteFromAllTables;

    /**
     * Delete alla tabled except tables listed in {@code DELETE_EXCLUDES_TABLE}
     *
     * @param connection
     */
    public void deleteFromAllTables(Connection connection, boolean deleteFromAllTables) {
        this.deleteFromAllTables = deleteFromAllTables;
        LOG.debug("Deleting from all tables");
        try (java.sql.Statement statement = connection.createStatement()) {
            deleteFromTables(connection, statement, getDeleteFromTableStatements(connection), 0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        LOG.debug("Deleted from all tables");
    }

    private void deleteFromTables(Connection connection, java.sql.Statement statement, List<String> stmts,
            int startIndex) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Deleting from tables");
        }
        int numExecuted = startIndex;
        try {
            for (String str : stmts) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Executing {}", str);
                }
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
                    if (deleteFromAllTables || isOkToDeleteFrom(tablename)) {
                        String stmt = "delete from " + tablename;
                        deleteFromTableStatements.add(stmt);
                        LOG.debug("Preparing delete statement: {}", stmt);
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
}
