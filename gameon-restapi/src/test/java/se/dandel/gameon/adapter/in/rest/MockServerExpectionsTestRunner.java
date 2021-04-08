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
import se.dandel.gameon.domain.model.Team;
import se.dandel.gameon.domain.model.TestBettingGameFactory;
import se.dandel.gameon.domain.model.TestTournamentFactory;
import se.dandel.gameon.domain.model.Tournament;
import se.dandel.gameon.domain.model.bet.BettingGame;
import se.dandel.gameon.domain.model.bet.BettingGameUser;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import java.util.*;

import static java.util.stream.Collectors.toList;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static se.dandel.gameon.domain.model.TestTeamFactory.createTeam;

class MockServerExpectionsTestRunner {
    final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private static final JsonbConfig JSONB_CONFIG = new JsonbConfig().withFormatting(true);

    private static final Jsonb jsonb = JsonbBuilder.create(JSONB_CONFIG);

    private TournamentModelMapper tournamentModelMapper = new TournamentModelMapperImpl();

    private TeamModelMapper teamModelMapper = new TeamModelMapperImpl();

    private BettingGameUserModelMapper bettingGameUserModelMapper = new BettingGameUserModelMapperImpl();


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
        expectations.add(createExpectationGameUser17());
        expectations.add(createPostExpectationGameUser17());
        client.upsert(expectations.toArray(Expectation[]::new));

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
        BettingGameUser bettingGameUser = bettingGame.getParticipants().iterator().next();
        bettingGameUser.setPk(17);
        BettingGameUserModel bettingGameUserModel = bettingGameUserModelMapper.toModel(bettingGameUser);
        String expected = jsonb.toJson(bettingGameUserModel);

        Expectation expectation = createExpectation(expectationId, expected, "/api/game/user/{pk}", Parameter.param("pk", String.valueOf(bettingGameUser.getPk())));
        return expectation;
    }

    private Expectation createExpectationTeams() {
        List<Team> teams1 = Arrays.asList(createTeam("Borussia Dortmund U19"), createTeam("RB Leipzig U19"));
        Collection<TeamModel> teams = teams1.stream().map(team -> teamModelMapper.toModel(team)).collect(toList());
        String expected = jsonb.toJson(teams);
        Expectation expectation = createExpectation("teams", expected, "/api/team");
        return expectation;
    }

    private Collection<Expectation> createExpecationTournaments() {
        Collection<Expectation> expectations = new ArrayList<>();
        {
            Tournament tournament = TestTournamentFactory.createTournamentWorldCup2018();
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
        Expectation expectation = createExpectation(String.format("tournament-pk-%d", tournament.getPk()), expected, path, parameter);
        return expectation;
    }

    private Expectation createAllTournaments(Tournament... tournament) {
        Collection<TournamentModel> tournaments = Collections.singleton(tournamentModelMapper.toModel(tournament[0]));
        String expected = jsonb.toJson(tournaments);
        String path = "/tournament";
        String expectationId = "tournament-all";
        Expectation expectation = createExpectation(expectationId, expected, path, null);
        expectation = expectation.withPriority(-100);
        return expectation;
    }

    private Expectation createExpectation(String expectationId, String expected, String path) {
        return createExpectation(expectationId, expected, path, null);
    }

    private Expectation createExpectation(String expectationId, String expected, String path, Parameter parameter) {
        LOGGER.debug("Creating expectation {} with path {} and parameters {} with response\n---{}\n---", expectationId, path, parameter, expected);
        HttpRequest request = request()
                .withMethod("GET")
                .withPath(path);
        if (parameter != null) {
            request = request.withPathParameter(parameter);
        }
        return Expectation.when(request).thenRespond(
                response()
                        .withStatusCode(200)
                        .withContentType(MediaType.APPLICATION_JSON)
                        .withBody(expected)
        ).withId(expectationId);
    }

}
