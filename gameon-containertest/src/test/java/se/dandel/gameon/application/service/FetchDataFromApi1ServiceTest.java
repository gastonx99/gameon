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
import se.dandel.gameon.domain.model.Country;
import se.dandel.gameon.domain.model.Team;
import se.dandel.gameon.domain.model.Tournament;
import se.dandel.gameon.domain.repository.AllPurposeTestRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@ContainerTest
@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = {9999})
class FetchDataFromApi1ServiceTest {

    private static final String COUNTRY_CODE = "sv";

    @Inject
    FetchDataFromApi1Service service;

    @Inject
    PersistenceTestManager persistManager;

    @Inject
    EntityManager entityManager;

    @Inject
    AllPurposeTestRepository allPurposeTestRepository;

    private MockServerClient mockServerClient;

    @BeforeEach
    public void beforeEach(MockServerClient client) throws Exception {
        this.mockServerClient = client;
    }

    @Test
    void fetchAndSaveCountries() throws Exception {
        // Given
        mockServerClient.upsert(createExpectation("api1-countries", "/api1/soccer/countries", "/json/api1/countries.json"));

        // When
        service.fetchAndSaveCountries();

        // Then
        Collection<Country> actuals = allPurposeTestRepository.findAll(Country.class);
        assertThat(actuals.size(), is(equalTo(43)));
    }

    @Test
    void fetchAndSaveTeams() throws Exception {
        // Given
        mockServerClient.upsert(createExpectation("api1-teams", "/api1/soccer/teams", "/json/api1/teams.json"));
        entityManager.persist(createCountry());

        // When
        service.fetchAndSaveTeams(COUNTRY_CODE);

        // Then
        Collection<Team> actuals = allPurposeTestRepository.findAll(Team.class);
        assertThat(actuals.size(), is(equalTo(3)));
    }

    @Test
    void fetchAndSaveLeagues() throws Exception {
        // Given
        mockServerClient.upsert(createExpectation("api1-leagues", "/api1/soccer/leagues", "/json/api1/leagues.json"));
        entityManager.persist(createCountry());

        // When
        service.fetchAndSaveLeagues(COUNTRY_CODE);

        // Then
        Collection<Tournament> actuals = allPurposeTestRepository.findAll(Tournament.class);
        assertThat(actuals.size(), is(equalTo(6)));
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

    private Country createCountry() {
        Country country = new Country();
        country.setName("Sweden");
        country.setCountryCode(COUNTRY_CODE);
        country.setRemoteKey("114");
        country.setContinent("Europe");
        return country;
    }

}