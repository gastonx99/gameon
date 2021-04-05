package se.dandel.gameon.adapter.out.rest.api1;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.mock.Expectation;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.MediaType;
import org.mockserver.model.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import java.io.FileInputStream;
import java.io.IOException;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

class Api1MockServerExpectionsTestRunner {
    final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private static final JsonbConfig JSONB_CONFIG = new JsonbConfig();

    private static final Jsonb jsonb = JsonbBuilder.create(JSONB_CONFIG);

    @Test
    @Disabled
    void mockAll() throws Exception {
        // Given
        MockServerClient client = new MockServerClient("localhost", 9080);

        // When
        client.upsert(createExpectationCountries(),
                createExpectationTeams("48"),
                createExpectationTeams("114"),
                createExpectationLeagues("48"),
                createExpectationLeagues("114"),
                createExpectationSeasons("567"),
                createExpectationMatches("875"),
                createExpectationMatches("1748")
        );

        // Then
    }

    private Expectation createExpectationCountries() throws Exception {
        String filename = "D:/gaston/restapi/sportdataapi/countries.json";
        String path = "/api1/api/v1/soccer/countries";
        String expectationId = "api1-countries";
        return createExpectation(expectationId, path, null, filename);
    }

    private Expectation createExpectationTeams(String countryId) throws Exception {
        String filename = String.format("D:/gaston/restapi/sportdataapi/teams-countryid-%s.json", countryId);
        String expectationId = String.format("api1-teams-countryid-%s", countryId);
        String path = "/api1/api/v1/soccer/teams";
        Parameter parameter = Parameter.param("country_id", countryId);

        return createExpectation(expectationId, path, parameter, filename);
    }

    private Expectation createExpectationLeagues(String countryId) throws Exception {
        String filename = String.format("D:/gaston/restapi/sportdataapi/leagues-countryid-%s.json", countryId);
        String expectationId = String.format("api1-leagues-countryid-%s", countryId);
        String path = "/api1/api/v1/soccer/leagues";
        Parameter parameter = Parameter.param("country_id", countryId);

        return createExpectation(expectationId, path, parameter, filename);
    }

    private Expectation createExpectationSeasons(String leagueId) throws Exception {
        String filename = String.format("D:/gaston/restapi/sportdataapi/seasons-leagueid-%s.json", leagueId);
        String expectationId = String.format("api1-seasons-leagueid-%s", leagueId);
        String path = "/api1/api/v1/soccer/seasons";
        Parameter parameter = Parameter.param("league_id", leagueId);
        return createExpectation(expectationId, path, parameter, filename);
    }

    private Expectation createExpectationMatches(String seasonId) throws Exception {
        String filename = String.format("D:/gaston/restapi/sportdataapi/matches-seasonid-%s.json", seasonId);
        String expectationId = String.format("api1-matches-seasonid-%s", seasonId);
        String path = "/api1/api/v1/soccer/matches";
        Parameter parameter = Parameter.param("season_id", seasonId);
        return createExpectation(expectationId, path, parameter, filename);
    }

    private Expectation createExpectation(String expectationId, String path, Parameter parameter, String filename) throws IOException {
        String json;
        try (FileInputStream fis = new FileInputStream(filename)) {
            json = IOUtils.toString(fis, "UTF-8");
        }
        HttpRequest request = request()
                .withMethod("GET")
                .withPath(path);
        if (parameter != null) {
            request = request.withQueryStringParameters(parameter);
        }
        Expectation expectation = Expectation.when(
                request
        ).thenRespond(
                response()
                        .withStatusCode(200)
                        .withContentType(MediaType.APPLICATION_JSON)
                        .withBody(json)
        ).withId(expectationId);
        return expectation;
    }


}