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
import se.dandel.gameon.domain.model.RemoteKey;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@ContainerTest
@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = {9999})
class FetchDataFromApi1ServiceTest {

    private static final RemoteKey REMOTE_KEY_COUNTRY = RemoteKey.of(48);

    private static final String LEAGUE_ID = "567";

    @Inject
    private FetchDataFromApi1Service service;

    @Inject
    private JMSProducer jmsProducer;

    @Inject
    @Named("Team.Q")
    private Queue teamQueue;

    @Inject
    @Named("Tournament.Q")
    private Queue tournamentQueue;

    @Inject
    @Named("Season.Q")
    private Queue seasonQueue;

    @Inject
    @Named("Match.Q")
    private Queue matchQueue;

    @Inject
    @Named("Country.Q")
    private Queue countryQueue;

    private MockServerClient mockServerClient;

    @BeforeEach
    public void beforeEach(MockServerClient client) throws Exception {
        this.mockServerClient = client;
    }

    @Test
    void fetchCountries() throws Exception {
        // Given
        mockServerClient.upsert(createExpectation("api1-countries", "/api1/soccer/countries", "/json/api1/countries.json"));

        // When
        service.fetchCountries();

        // Then
        verify(jmsProducer, times(43)).send(eq(countryQueue), anyString());
    }

    @Test
    void fetchTeams() throws Exception {
        // Given
        mockServerClient.upsert(createExpectation("api1-teams", "/api1/soccer/teams", "/json/api1/teams.json"));

        // When
        service.fetchTeams(REMOTE_KEY_COUNTRY);

        // Then
        verify(jmsProducer, times(3)).send(eq(teamQueue), anyString());
    }

    @Test
    void fetchAllLeagues() throws Exception {
        // Given
        mockServerClient.upsert(createExpectation("api1-leagues", "/api1/soccer/leagues", "/json/api1/leagues.json"));

        // When
        service.fetchLeagues(Optional.empty());

        // Then
        verify(jmsProducer, times(6)).send(eq(tournamentQueue), anyString());
    }

    @Test
    void fetchLeaguesForCountry() throws Exception {
        // Given
        mockServerClient.upsert(createExpectation("api1-leagues", "/api1/soccer/leagues", "/json/api1/leagues.json"));

        // When
        service.fetchLeagues(Optional.of(REMOTE_KEY_COUNTRY));

        // Then
        verify(jmsProducer, times(6)).send(eq(tournamentQueue), anyString());
    }

    @Test
    void fetchSeasons() throws Exception {
        // Given
        mockServerClient.upsert(createExpectation("api1-seasons", "/api1/soccer/seasons", "/json/api1/seasons.json"));

        // When
        service.fetchSeasons(RemoteKey.of(LEAGUE_ID));

        // Then
        verify(jmsProducer, times(3)).send(eq(seasonQueue), anyString());
    }

    @Test
    void fetchMatches() throws Exception {
        // Given
        mockServerClient.upsert(createExpectation("api1-matches", "/api1/soccer/matches", "/json/api1/matches.json"));

        // When
        service.fetchMatches(RemoteKey.of(1));

        // Then
        verify(jmsProducer, times(3)).send(eq(matchQueue), anyString());
    }


    private Expectation createExpectation(String expectationId, String path, String resource) throws Exception {
        String expected = IOUtils.toString(getClass().getResourceAsStream(resource), "UTF-8");
        Expectation expectation = Expectation.when(
                request()
                        .withMethod("GET")
                        .withPath(path)
        ).thenRespond(
                response()
                        .withStatusCode(200)
                        .withContentType(MediaType.APPLICATION_JSON)
                        .withBody(expected)
        ).withId(expectationId);
        return expectation;
    }

}