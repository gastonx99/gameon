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
import se.dandel.gameon.domain.model.*;
import se.dandel.gameon.domain.repository.AllPurposeTestRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static se.dandel.gameon.domain.model.TestTeamFactory.createTeam;

@ContainerTest
@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = {9999})
class FetchDataFromApi1ServiceTest {

    private static final String COUNTRY_CODE = "se";

    private static final String LEAGUE_ID = "567";

    private static final String SEASON_ID = "42";

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

    @Test
    void fetchAndSaveSeasons() throws Exception {
        // Given
        mockServerClient.upsert(createExpectation("api1-seasons", "/api1/soccer/seasons", "/json/api1/seasons.json"));
        entityManager.persist(createLeague());

        // When
        service.fetchAndSaveSeasons(RemoteKey.of(LEAGUE_ID));

        // Then
        Collection<Season> actuals = allPurposeTestRepository.findAll(Season.class);
        assertThat(actuals.size(), is(equalTo(3)));
    }

    @Test
    void fetchAndSaveMatches() throws Exception {
        // Given
        mockServerClient.upsert(createExpectation("api1-matches", "/api1/soccer/matches", "/json/api1/matches.json"));
        Season season = createSeason(createLeague());
        entityManager.persist(season.getTournament());

        Arrays.asList(
                createTeam("3993", "1. FC Union Berlin"), createTeam("4075", "FC Augsburg"),
                createTeam("4070", "Werder Bremen"), createTeam("4067", "Hertha BSC"),
                createTeam("3991", "1. FC Cologne"), createTeam("4079", "TSG 1899 Hoffenheim")
        ).forEach(team -> allPurposeTestRepository.persist(team));

        // When
        service.fetchAndSaveMatches(season.getRemoteKey());

        // Then
        Collection<Match> actuals = allPurposeTestRepository.findAll(Match.class);
        assertThat(actuals.size(), is(equalTo(3)));
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
        country.setRemoteKey(RemoteKey.of("114"));
        country.setContinent("Europe");
        return country;
    }

    private Tournament createLeague() {
        Tournament tournament = new Tournament(TournamentType.LEAGUE);
        tournament.setName("Allsvenskan");
        tournament.setRemoteKey(RemoteKey.of(LEAGUE_ID));
        return tournament;
    }

    private Season createSeason(Tournament league) {
        Season season = new Season();
        season.setName("Allsvenskan");
        season.setRemoteKey(RemoteKey.of(SEASON_ID));
        season.setTournament(league);
        league.addSeason(season);
        return season;
    }


}