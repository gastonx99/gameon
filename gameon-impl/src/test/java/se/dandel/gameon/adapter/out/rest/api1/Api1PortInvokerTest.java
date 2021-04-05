package se.dandel.gameon.adapter.out.rest.api1;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockserver.client.MockServerClient;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.mockserver.mock.Expectation;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.MediaType;
import org.mockserver.model.Parameter;
import se.dandel.gameon.adapter.EnvironmentConfig;
import se.dandel.gameon.domain.GameonRuntimeException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static se.dandel.gameon.adapter.out.rest.api1.Api1PortAdapter.ENVELOPE_LEAGUES;
import static se.dandel.gameon.adapter.out.rest.api1.Api1PortAdapter.PATH_SOCCER_LEAGUES;

@ExtendWith(MockitoExtension.class)
@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = {9999})
class Api1PortInvokerTest {

    private static final String API_PREFIX = "/api/v1";

    private static final String BASE_URL = "http://localhost:9999" + API_PREFIX;

    private static final String JSON = "/json/api1/leagues.json";

    public static final String PATH = "/soccer/leagues";

    @Mock
    private EnvironmentConfig environmentConfig;

    @InjectMocks
    private Api1PortInvoker invoker;

    private MockServerClient mockServerClient;

    private List<Parameter> queryParams = new ArrayList<>();

    @BeforeEach
    void setUp(MockServerClient mockServerClient) {
        when(environmentConfig.getApi1BaseUrl()).thenReturn(BASE_URL);
        this.mockServerClient = mockServerClient;
    }

    @Test
    void invoke() throws Exception {
        // Given
        mockServerClient.upsert(createExpectation(PATH));

        // When
        Collection<LeagueDTO> actuals = invoker.invoke(ENVELOPE_LEAGUES, PATH_SOCCER_LEAGUES, Collections.emptyMap());

        // Then
        assertThat(actuals.size(), is(equalTo(6)));
    }

    @Test
    void invokeWithQueryParam() throws Exception {
        // Given
        queryParams.add(Parameter.param("country_id", "114"));
        mockServerClient.upsert(createExpectation(PATH));

        // When
        Collection<LeagueDTO> actuals = invoker.invoke(ENVELOPE_LEAGUES, PATH_SOCCER_LEAGUES, Collections.singletonMap("country_id", "114"));

        // Then
        assertThat(actuals.size(), is(equalTo(6)));
    }

    @Test
    void invokeNotFound() throws Exception {
        // Given
        mockServerClient.upsert(createExpectation("/wrong/path"));

        // When
        GameonRuntimeException actual = assertThrows(GameonRuntimeException.class, () -> invoker.invoke(ENVELOPE_LEAGUES, PATH_SOCCER_LEAGUES, Collections.emptyMap()));
        assertTrue(actual.getMessage().contains("404"));
    }

    private Expectation createExpectation(String path) throws Exception {
        String expected = IOUtils.toString(getClass().getResourceAsStream(JSON), "UTF-8");
        HttpRequest request = request()
                .withMethod("GET")
                .withPath(API_PREFIX + path)
                .withQueryStringParameters(queryParams);
        Expectation expectation = Expectation.when(
                request
        ).thenRespond(
                response()
                        .withStatusCode(200)
                        .withContentType(MediaType.APPLICATION_JSON)
                        .withBody(expected)
        ).withId("expectationId");
        return expectation;
    }

}