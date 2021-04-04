package se.dandel.gameon.adapter.cli;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.bridge.SLF4JBridgeHandler;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FetchDataFromApi1CliRunner {

    static {
        SLF4JBridgeHandler.install();
    }

    private static final String GAMEON_PROPERTIES = "D:/gaston/restapi/gameon.properties";

    @Test
    @Disabled("Test is run manually only since it makes a live connection")
    void country() {
        // Given
        String[] args = {"-t", "country", "-p", GAMEON_PROPERTIES};

        // When
        int actual = FetchDataFromApi1Cli.mainInner(args);
        assertThat(actual, is(equalTo(0)));
    }

    @Test
    @Disabled("Test is run manually only since it makes a live connection")
    void team() {
        // Given
        String[] args = {"-t", "team", "-p", GAMEON_PROPERTIES, "-a", "countrycode=se"};

        // When
        int actual = FetchDataFromApi1Cli.mainInner(args);
        assertThat(actual, is(equalTo(0)));
    }

    @Test
    @Disabled("Test is run manually only since it makes a live connection")
    void league() {
        // Given
        String[] args = {"-t", "league", "-p", GAMEON_PROPERTIES, "-a", "countrycode=se"};

        // When
        int actual = FetchDataFromApi1Cli.mainInner(args);
        assertThat(actual, is(equalTo(0)));
    }
}
