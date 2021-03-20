package se.dandel.gameon.adapter.out.rest.api2;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class JsonApi2ParserTest {
    final Logger LOGGER = LoggerFactory.getLogger(getClass());

    JsonApi2Parser parser = new JsonApi2Parser();

    @Test
    void gamesInSeason() throws Exception {
        // Given
        String json = IOUtils.toString(getClass().getResource("/json/api2/gamesInSeason.json"), "UTF-8");

        // When
        Collection<GameDTO> actuals = parser.parseGamesInSeason(json);

        // Then
        assertThat(actuals.size(), is(equalTo(3)));
        LOGGER.debug("Actuals: {}", actuals);
    }
}