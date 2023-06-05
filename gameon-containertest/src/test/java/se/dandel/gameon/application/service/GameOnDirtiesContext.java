package se.dandel.gameon.application.service;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GameOnDirtiesContext implements BeforeEachCallback {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameOnDirtiesContext.class);

    private static final Collection<String> EXCLUDED_TABLES = List.of("DATABASECHANGELOG", "DATABASECHANGELOGLOCK");

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        LOGGER.atInfo().log("Rensa föregående testdata");
        Connection connection = SpringExtension.getApplicationContext(extensionContext).getBean(DataSource.class).getConnection();
        Collection<String> tablesToClean = new ArrayList<>();
        ResultSet tables = connection.getMetaData().getTables(null, null, null, null);
        while (tables.next()) {
            String tableType = tables.getString("TABLE_TYPE");
            if ("TABLE".equals(tableType)) {
                tablesToClean.add(tables.getString("TABLE_NAME"));
            }
        }

        tablesToClean.removeAll(EXCLUDED_TABLES);

        try (Statement statement = connection.createStatement()) {
            deleteFromTables(statement, tablesToClean);
            connection.commit();
        }
    }

    private void deleteFromTables(Statement statement, Collection<String> tables) {
        LOGGER.atDebug().log("Deleting from tables");
        Collection<String> remainingTables = new ArrayList<>();
        tables.forEach(table -> {
            String str = "delete from " + table;
            LOGGER.atDebug().log("Executing delete from {}", str);
            try {
                statement.execute(str);
            } catch (SQLException e) {
                remainingTables.add(table);
            }
        });
        if (remainingTables.size() == tables.size()) {
            throw new IllegalStateException("Unable to delete from tables " + remainingTables);
        }
        if (!remainingTables.isEmpty()) {
            deleteFromTables(statement, remainingTables);
        }
    }

}