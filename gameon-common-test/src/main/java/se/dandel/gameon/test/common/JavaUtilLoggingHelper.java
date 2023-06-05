package se.dandel.gameon.test.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;

public class JavaUtilLoggingHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(JavaUtilLoggingHelper.class);

    private static boolean reloaded;

    public static void reloadButOnlyOnce(String julPropertiesFilename) {
        if (reloaded) {
            return;
        }
        reloaded = true;
        LOGGER.atDebug().log("Reloading JUL using {}", julPropertiesFilename);
        InputStream stream = JavaUtilLoggingHelper.class.getClassLoader().getResourceAsStream(julPropertiesFilename);
        try {
            LogManager.getLogManager().reset();
            LogManager.getLogManager().updateConfiguration(stream, null);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
