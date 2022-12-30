package se.dandel.gameon.adapter.in.rest;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.mock.Expectation;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.MediaType;
import org.mockserver.model.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.dandel.gameon.domain.model.*;
import se.dandel.gameon.domain.model.bet.BettingGame;
import se.dandel.gameon.domain.model.bet.BettingGameUser;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

class MockServerExpectionsTestRunner {
    final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private static final JsonbConfig JSONB_CONFIG = new JsonbConfig().withFormatting(true);

    private static final Jsonb jsonb = JsonbBuilder.create(JSONB_CONFIG);

    private TournamentModelMapper tournamentModelMapper = new TournamentModelMapperImpl();

    private TeamModelMapper teamModelMapper = new TeamModelMapperImpl();

    private BettingGameUserModelMapper bettingGameUserModelMapper = new BettingGameUserModelMapperImpl();

    private BettingGameUserWidgetModelMapper bettingGameUserWidgetModelMapper = new BettingGameUserWidgetModelMapperImpl();

    @Test
    @Disabled
    void mockAll() {
        // Given
        MockServerClient client = new MockServerClient("localhost", 9080);
        client.reset();

        // When
        Collection<Expectation> expectations = new ArrayList<>();
        expectations.addAll(createExpecationTournaments());
        expectations.add(createExpectationTeams());
        expectations.addAll(createExpectationGames());
        expectations.addAll(createExpectationGameUser1());
        expectations.addAll(createExpectationGameUser2());
        expectations.addAll(createExpectationGameUser3());
        client.upsert(expectations.toArray(Expectation[]::new));

        // Then
    }

    private Collection<Expectation> createExpectationGames() {
        BettingGame bettingGame1 = TestBettingGameFactory.createBettingGamePremierLeague20202021();
        BettingGameUser bettingGameUser1 = bettingGame1.getParticipants().iterator().next();
        bettingGameUser1.setPk(1);
        BettingGame bettingGame2 = TestBettingGameFactory.createBettingGameWorldCup2018();
        BettingGameUser bettingGameUser2 = bettingGame2.getParticipants().iterator().next();
        bettingGameUser2.setPk(2);
        BettingGame bettingGame3 = TestBettingGameFactory.createBettingGameEuro2021();
        BettingGameUser bettingGameUser3 = bettingGame3.getParticipants().iterator().next();
        bettingGameUser3.setPk(3);

        BettingGameUserWidgetModel modelActiveGames = bettingGameUserWidgetModelMapper.toModel(List.of(bettingGameUser1, bettingGameUser3));
        BettingGameUserWidgetModel modelCompletedGames = bettingGameUserWidgetModelMapper.toModel(List.of(bettingGameUser2));

        Expectation expectationActiveGames = createExpectation("game-user-active",
                jsonb.toJson(modelActiveGames),
                "/api/game/user",
                Collections.emptyList(),
                Collections.singletonList(Parameter.param("gamestatus", "active")));
        Expectation expectationCompletedGames = createExpectation("game-user-completed",
                jsonb.toJson(modelCompletedGames),
                "/api/game/user",
                Collections.emptyList(),
                Collections.singletonList(Parameter.param("gamestatus", "completed")));
        return List.of(expectationActiveGames, expectationCompletedGames);
    }

    private Collection<Expectation> createExpectationGameUser1() {
        BettingGame bettingGame = TestBettingGameFactory.createBettingGamePremierLeague20202021();
        BettingGameUser bettingGameUser = bettingGame.getParticipants().iterator().next();
        bettingGameUser.setPk(1);
        return List.of(createExpectationGameUser(bettingGameUser, "GET"), createExpectationGameUser(bettingGameUser, "POST"));
    }

    private Collection<Expectation> createExpectationGameUser2() {
        BettingGame bettingGame = TestBettingGameFactory.createBettingGameWorldCup2018();
        BettingGameUser bettingGameUser = bettingGame.getParticipants().iterator().next();
        bettingGameUser.setPk(2);
        return List.of(createExpectationGameUser(bettingGameUser, "GET"), createExpectationGameUser(bettingGameUser, "POST"));
    }

    private Collection<Expectation> createExpectationGameUser3() {
        BettingGame bettingGame = TestBettingGameFactory.createBettingGameEuro2021();
        BettingGameUser bettingGameUser = bettingGame.getParticipants().iterator().next();
        bettingGameUser.setPk(3);
        return List.of(createExpectationGameUser(bettingGameUser, "GET"), createExpectationGameUser(bettingGameUser, "POST"));
    }


    private Expectation createExpectationGameUser(BettingGameUser bettingGameUser, String method) {
        String expectationId = String.format("game-user-%d", bettingGameUser.getPk());
        if ("POST".equals(method)) {
            expectationId += "-post";
        }
        BettingGameUserModel bettingGameUserModel = bettingGameUserModelMapper.toModel(bettingGameUser);
        String expected = jsonb.toJson(bettingGameUserModel);

        Parameter parameter = Parameter.param("pk", String.valueOf(bettingGameUser.getPk()));
        Expectation expectation = createExpectation(expectationId, expected, "/api/game/user/{pk}", Collections.singletonList(parameter), Collections.emptyList());
        return expectation;
    }

    private Expectation createExpectationTeams() {
        List<Team> teams = TestTeamFactory.createTeamsEuro2020();
        Collection<TeamModel> models = teams.stream().map(team -> teamModelMapper.toModel(team)).collect(toList());
        String expected = jsonb.toJson(models);
        Expectation expectation = createExpectation("teams", expected, "/api/team", Collections.emptyList(), Collections.emptyList());
        return expectation;
    }

    private Collection<Expectation> createExpecationTournaments() {
        Collection<Expectation> expectations = new ArrayList<>();
        {
            Tournament tournament = TestTournamentFactory.createCupWorldCup2018();
            tournament.setPk(1);
            expectations.add(createTournament(tournament));
        }
        {
            Tournament tournament = TestTournamentFactory.createTournamentPremierLeague20202021();
            tournament.setPk(2);
            expectations.add(createTournament(tournament));
        }
        return expectations;
    }

    private Expectation createTournament(Tournament tournament) {
        Parameter parameter = Parameter.param("pk", String.valueOf(tournament.getPk()));
        String path = "/api/tournament/{pk}";
        Collection<TournamentModel> tournaments = Collections.singleton(tournamentModelMapper.toModel(tournament));
        String expected = jsonb.toJson(tournaments);
        Expectation expectation = createExpectation(String.format("tournament-pk-%d", tournament.getPk()), expected, path, Collections.singletonList(parameter), Collections.emptyList());
        return expectation;
    }

    private Expectation createAllTournaments(Tournament... tournament) {
        Collection<TournamentModel> tournaments = Collections.singleton(tournamentModelMapper.toModel(tournament[0]));
        String expected = jsonb.toJson(tournaments);
        String path = "/tournament";
        String expectationId = "tournament-all";
        Expectation expectation = createExpectation(expectationId, expected, path, Collections.emptyList(), Collections.emptyList());
        return expectation;
    }

    private Expectation createExpectation(String expectationId, String expected, String path, List<Parameter> pathParameters, List<Parameter> qsParameters) {
        LOGGER.debug("Creating expectation {} with path {} and path parameters {} and querystring parameters {} with response\n---{}\n---", expectationId, path, pathParameters, qsParameters, expected);
        HttpRequest request = request()
                .withMethod("GET")
                .withPath(path)
                .withPathParameters(pathParameters)
                .withQueryStringParameters(qsParameters);
        return Expectation.when(request).thenRespond(
                response()
                        .withStatusCode(200)
                        .withContentType(MediaType.APPLICATION_JSON)
                        .withBody(expected)
        ).withId(expectationId).withPriority(pathParameters.isEmpty() ? -100 : 0);
    }

}
