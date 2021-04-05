package se.dandel.gameon.adapter.cli;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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
    void all() {
        country();
        team("se");
        league("se");
        season("567");
        match("875");
        match("1748");
    }

    @Test
    @Disabled("Test is run manually only since it makes a live connection")
    void country() {
        // Given
        String[] args = {"-t", "country", "-p", GAMEON_PROPERTIES};

        // When
        int actual = FetchDataFromApi1Cli.mainInner(args);
        assertThat(actual, is(equalTo(0)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"se"})
    @Disabled("Test is run manually only since it makes a live connection")
    void team(String countryCode) {
        // Given
        String[] args = {"-t", "team", "-p", GAMEON_PROPERTIES, "-a", "countrycode=" + countryCode};

        // When
        int actual = FetchDataFromApi1Cli.mainInner(args);
        assertThat(actual, is(equalTo(0)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"se"})
    @Disabled("Test is run manually only since it makes a live connection")
    void league(String countryCode) {
        // Given
        String[] args = {"-t", "league", "-p", GAMEON_PROPERTIES, "-a", "countrycode=" + countryCode};

        // When
        int actual = FetchDataFromApi1Cli.mainInner(args);
        assertThat(actual, is(equalTo(0)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"567"})
    @Disabled("Test is run manually only since it makes a live connection")
    void season(String leagueId) {
        // Given
        String[] args = {"-t", "season", "-p", GAMEON_PROPERTIES, "-a", "leagueid=" + leagueId};

        // When
        int actual = FetchDataFromApi1Cli.mainInner(args);
        assertThat(actual, is(equalTo(0)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"875"})
    @Disabled("Test is run manually only since it makes a live connection")
    void match(String seasonId) {
        // Given
        String[] args = {"-t", "match", "-p", GAMEON_PROPERTIES, "-a", "seasonid=" + seasonId};

        // When
        int actual = FetchDataFromApi1Cli.mainInner(args);
        assertThat(actual, is(equalTo(0)));
    }

}
