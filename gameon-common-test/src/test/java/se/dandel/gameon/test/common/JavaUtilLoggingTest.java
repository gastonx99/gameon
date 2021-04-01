package se.dandel.gameon.test.common;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;

public class JavaUtilLoggingTest {

    @Test
    void usingDistributionDefaultProperties() {
        // Given
        java.util.logging.Logger logger = java.util.logging.Logger.getLogger(getClass().getName());

        // When
        logger.info("usingDistributionDefaultProperties");
        logger.log(Level.FINE, "Logging something fine which is not logged since default properties has INFO level");
        logger.log(Level.INFO, "Logging something info");

        // Then
    }

    @Test
    void usingClasspathProperties() throws Exception {
        // Given
        InputStream stream = getClass().getClassLoader().getResourceAsStream("logging-console.properties");
        LogManager.getLogManager().readConfiguration(stream);
        java.util.logging.Logger logger = java.util.logging.Logger.getLogger(getClass().getName());

        // When
        logger.info("usingClasspathProperties");
        logger.log(Level.FINE, "Logging something fine");
        logger.log(Level.INFO, "Logging something info");

        // Then
    }

    @Test
    void usingSlf4jBridge() throws Exception {
        // Given
        InputStream stream = getClass().getClassLoader().getResourceAsStream("logging-slf4j-bridge.properties");
        LogManager.getLogManager().readConfiguration(stream);
        java.util.logging.Logger logger = java.util.logging.Logger.getLogger(getClass().getName());

        // When
        logger.info("usingSlf4jBridge");
        logger.log(Level.FINE, "Logging something fine");
        logger.log(Level.INFO, "Logging something info");

        // Then
    }

    @Test
    void usingSlf4jBridgeAfterDefaultsAreLoaded() throws Exception {
        // Given
        java.util.logging.Logger logger = java.util.logging.Logger.getLogger(getClass().getName());
        logger.info("usingSlf4jBridge");
        InputStream stream = getClass().getClassLoader().getResourceAsStream("logging-slf4j-bridge.properties");
        LogManager.getLogManager().readConfiguration(stream);

        // When
        logger = java.util.logging.Logger.getLogger(getClass().getName());
        logger.log(Level.FINE, "Logging something fine");
        logger.log(Level.INFO, "Logging something info");

        // Then
        JavaUtilLoggingHelper.reloadButOnlyOnce("logging-slf4j-bridge.properties");

        // When
        logger.log(Level.FINE, "Logging something fine");
        logger.log(Level.INFO, "Logging something info");
    }
}
