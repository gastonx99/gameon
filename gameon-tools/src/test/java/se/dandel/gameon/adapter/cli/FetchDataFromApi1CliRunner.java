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
        team("48"); // Germany
        team("114"); // Sweden
        league("4"); // Euro
        league("48"); // Germany
        league("114"); // Sweden
        season("271"); // Euro
        season("567"); // Allsvenskan
        match("510"); // Euro 2021
        match("875"); // Allsvenskan 2020
        match("1748"); // Allsvenskan 2021
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
    @ValueSource(strings = {"114"})
    @Disabled("Test is run manually only since it makes a live connection")
    void team(String countryId) {
        // Given
        String[] args = {"-t", "team", "-p", GAMEON_PROPERTIES, "-a", "countryid=" + countryId};

        // When
        int actual = FetchDataFromApi1Cli.mainInner(args);
        assertThat(actual, is(equalTo(0)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"114"})
    @Disabled("Test is run manually only since it makes a live connection")
    void league(String countryId) {
        // Given
        String[] args = {"-t", "league", "-p", GAMEON_PROPERTIES, "-a", "countryid=" + countryId};

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
    @ValueSource(strings = {"510"})
    @Disabled("Test is run manually only since it makes a live connection")
    void match(String seasonId) {
        // Given
        String[] args = {"-t", "match", "-p", GAMEON_PROPERTIES, "-a", "seasonid=" + seasonId};

        // When
        int actual = FetchDataFromApi1Cli.mainInner(args);
        assertThat(actual, is(equalTo(0)));
    }

}
