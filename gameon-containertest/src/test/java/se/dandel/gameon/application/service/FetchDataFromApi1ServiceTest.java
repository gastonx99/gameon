package se.dandel.gameon.application.service;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.mockserver.mock.Expectation;
import org.mockserver.model.MediaType;
import se.dandel.gameon.ContainerTest;
import se.dandel.gameon.datamodel.test.jpa.PersistenceTestManager;

import javax.inject.Inject;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@ContainerTest
@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = {9999})
class FetchDataFromApi1ServiceTest {

    @Inject
    FetchDataFromApi1Service service;

    @Inject
    PersistenceTestManager persistManager;

    private MockServerClient mockServerClient;

    @BeforeEach
    public void beforeEach(MockServerClient client) throws Exception {
        this.mockServerClient = client;
        mockServerClient.upsert(createExpectationLeagues());
    }

    @Test
    void fetchTeams() {
        // Given

        // When

        // Then
    }

    @Test
    void fetchLeagues() {
        // Given

        // When
        service.fetchLeagues();

        // Then
    }

    private Expectation createExpectationLeagues() throws Exception {
        String expected = IOUtils.toString(getClass().getResourceAsStream("/json/api1/leagues.json"), "UTF-8");
        Expectation expectation = Expectation.when(
                request()
                        .withMethod("GET")
                        .withPath("/api1/soccer/leagues")
        ).thenRespond(
                response()
                        .withStatusCode(200)
                        .withContentType(MediaType.APPLICATION_JSON)
                        .withBody(expected)
        ).withId("api1-leagues");
        return expectation;
    }

}