package se.dandel.gameon.adapter.in.rest;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.mock.Expectation;
import org.mockserver.model.HttpTemplate;
import org.mockserver.model.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.dandel.gameon.domain.model.TestBettingGameFactory;
import se.dandel.gameon.domain.model.TestTournamentFactory;
import se.dandel.gameon.domain.model.Tournament;
import se.dandel.gameon.domain.model.bet.BettingGame;
import se.dandel.gameon.domain.model.bet.BettingGameUser;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

class MockServerExpectionsTestRunner {
    final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private static final JsonbConfig JSONB_CONFIG = new JsonbConfig();

    private static final Jsonb jsonb = JsonbBuilder.create(JSONB_CONFIG);

    @Test
    @Disabled
    void mockAll() {
        // Given
        MockServerClient client = new MockServerClient("localhost", 9080);

        // When
        client.upsert(createExpectationTeams(), createExpecationTournaments(), createExpectationGameUser17());
        client.upsert(createPostExpectationGameUser17());

        // Then
    }

    private Expectation createPostExpectationGameUser17() {
        String expectationId = "game-user-17-post";
        return createExpectationGameUser17(expectationId, "POST");
    }

    private Expectation createExpectationGameUser17() {
        String expectationId = "game-user-17";
        return createExpectationGameUser17(expectationId, "GET");
    }

    private Expectation createExpectationGameUser17(String expectationId, String method) {
        BettingGame bettingGame = TestBettingGameFactory.createBettingGamePremierLeague20202021();
        LOGGER.debug("Betting game: {}", ReflectionToStringBuilder.toString(bettingGame, ToStringStyle.SHORT_PREFIX_STYLE));
        BettingGameUser bettingGameUser = bettingGame.getParticipants().iterator().next();
        BettingGameUserModelMapper bettingGameUserModelMapper = new BettingGameUserModelMapperImpl();
        BettingGameUserModel bettingGameUserModel = bettingGameUserModelMapper.toModel(bettingGameUser);

        String expected = jsonb.toJson(bettingGameUserModel);
        LOGGER.debug("JSON betting game: {}", expected);

        Expectation expectation = Expectation.when(
                request()
                        .withMethod(method)
                        .withPath("/api/game/user/17")
        ).thenRespond(
                response()
                        .withStatusCode(200)
                        .withContentType(MediaType.APPLICATION_JSON)
                        .withBody(expected)
        ).withId(expectationId);
        return expectation;
    }


    private Expectation createExpectationTemplatedTeams() {
        Collection<TournamentModel.TeamModel> teams = Arrays.asList(createTeam("Borussia Dortmund U19", "de"), createTeam("RB Leipzig U19", "de"));
        String expected = jsonb.toJson(teams);
        HttpTemplate template = new HttpTemplate(HttpTemplate.TemplateType.VELOCITY);
//        template.withTemplate();
        Expectation expectation = Expectation.when(
                request()
                        .withMethod("GET")
                        .withPath("/api/teams")
        ).thenRespond(template)
                .withId("teams");
        return expectation;
    }

    private Expectation createExpectationTeams() {
        Collection<TournamentModel.TeamModel> teams = Arrays.asList(createTeam("Borussia Dortmund U19", "de"), createTeam("RB Leipzig U19", "de"));
        String expected = jsonb.toJson(teams);
        Expectation expectation = Expectation.when(
                request()
                        .withMethod("GET")
                        .withPath("/api/teams")
        ).thenRespond(
                response()
                        .withStatusCode(200)
                        .withContentType(MediaType.APPLICATION_JSON)
                        .withBody(expected)
        ).withId("teams");
        return expectation;
    }

    private Expectation createExpecationTournaments() {
        Tournament tournament = TestTournamentFactory.createTournamentWorldCup2018();
        TournamentMapper mapper = new TournamentMapper();
        Collection<TournamentModel> tournaments = Collections.singleton(mapper.map(tournament));
        String expected = jsonb.toJson(tournaments);
        Expectation expectation = Expectation.when(
                request()
                        .withMethod("GET")
                        .withPath("/tournament/all")
        ).thenRespond(
                response()
                        .withStatusCode(200)
                        .withContentType(MediaType.APPLICATION_JSON)
                        .withBody(expected)
        ).withId("tournament-all");
        return expectation;
    }

    private TournamentModel.TeamModel createTeam(String name, String countryCode) {
        TournamentModel.TeamModel team = new TournamentModel.TeamModel();
        team.setPk(1);
        team.setName(name);
        team.setCountryCode(countryCode);
        return team;
    }
}