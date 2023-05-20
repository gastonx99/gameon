package se.dandel.gameon.application.service;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.mock.Expectation;
import org.mockserver.model.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import se.dandel.gameon.domain.model.RemoteKey;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;


@Integrationstest
class FetchDataFromApi1ServiceTest {

    private static final RemoteKey REMOTE_KEY_COUNTRY = RemoteKey.of(48);

    private static final String LEAGUE_ID = "567";

    @Autowired
    private FetchDataFromApi1Service service;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("gameon.queue.team")
    private String qTeam;

    @Value("gameon.queue.tournament")
    private String qTournament;

    @Value("gameon.queue.season")
    private String qSeason;

    @Value("gameon.queue.match")
    private String qMatch;

    @Value("gameon.queue.country")
    private String qCountry;

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
        verify(jmsTemplate, times(43)).send(eq(qCountry), any());
    }

    @Test
    void fetchTeams() throws Exception {
        // Given
        mockServerClient.upsert(createExpectation("api1-teams", "/api1/soccer/teams", "/json/api1/teams.json"));

        // When
        service.fetchTeams(REMOTE_KEY_COUNTRY);

        // Then
        verify(jmsTemplate, times(3)).send(eq(qTeam), any());
    }

    @Test
    void fetchAllLeagues() throws Exception {
        // Given
        mockServerClient.upsert(createExpectation("api1-leagues", "/api1/soccer/leagues", "/json/api1/leagues.json"));

        // When
        service.fetchLeagues(Optional.empty());

        // Then
        verify(jmsTemplate, times(6)).send(eq(qTournament), any());
    }

    @Test
    void fetchLeaguesForCountry() throws Exception {
        // Given
        mockServerClient.upsert(createExpectation("api1-leagues", "/api1/soccer/leagues", "/json/api1/leagues.json"));

        // When
        service.fetchLeagues(Optional.of(REMOTE_KEY_COUNTRY));

        // Then
        verify(jmsTemplate, times(6)).send(eq(qTournament), any());
    }

    @Test
    void fetchSeasons() throws Exception {
        // Given
        mockServerClient.upsert(createExpectation("api1-seasons", "/api1/soccer/seasons", "/json/api1/seasons.json"));

        // When
        service.fetchSeasons(RemoteKey.of(LEAGUE_ID));

        // Then
        verify(jmsTemplate, times(3)).send(eq(qSeason), any());
    }

    @Test
    void fetchMatches() throws Exception {
        // Given
        mockServerClient.upsert(createExpectation("api1-matches", "/api1/soccer/matches", "/json/api1/matches.json"));

        // When
        service.fetchMatches(RemoteKey.of(1));

        // Then
        verify(jmsTemplate, times(3)).send(eq(qMatch), any());
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