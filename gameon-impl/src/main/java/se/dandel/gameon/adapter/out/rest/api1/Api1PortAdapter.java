package se.dandel.gameon.adapter.out.rest.api1;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.logging.LoggingFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.dandel.gameon.adapter.EnvironmentConfig;
import se.dandel.gameon.domain.GameonRuntimeException;
import se.dandel.gameon.domain.model.Country;
import se.dandel.gameon.domain.model.Team;
import se.dandel.gameon.domain.model.Tournament;
import se.dandel.gameon.domain.port.Api1Port;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.bind.config.PropertyNamingStrategy;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class Api1PortAdapter implements Api1Port {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private static final JsonbConfig JSONB_CONFIG = new JsonbConfig().withPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CASE_WITH_UNDERSCORES);

    private static final Jsonb jsonb = JsonbBuilder.create(JSONB_CONFIG);


    @Inject
    private EnvironmentConfig environmentConfig;

    @Inject
    private TeamMapper teamMapper;

    @Inject
    private TournamentMapper tournamentMapper;

    @Inject
    private CountryMapper countryMapper;

    @Override
    public Collection<Team> fetchTeams(Country country) {
        WebTarget target = getBaseTarget().path("/soccer/teams");
        LOGGER.debug("Connecting to {}", target);
        String countryId = country.getRemoteKey();
        Invocation.Builder request = target.queryParam("country_id", countryId)
                .request(MediaType.APPLICATION_JSON)
                .header("apikey", environmentConfig.getAPi1Apikey());
        Response response = request.get();
        LOGGER.debug("Response status: {}", response.getStatus());
        if (response.getStatus() != 200) {
            throw new GameonRuntimeException("Unable to connect to target %s, got response status %d", target, response.getStatus());
        }

        String json = response.readEntity(String.class);
        LOGGER.debug("Response JSON: {}", json);

        EnvelopeDTO<TeamsQueryDTO, Collection<TeamDTO>> envelopeDTO = jsonb.fromJson(json, new EnvelopeDTO<TeamsQueryDTO, Collection<TeamDTO>>() {
        }.getClass().getGenericSuperclass());

        Collection<TeamDTO> dtos = envelopeDTO.getData();

        LOGGER.debug("Number of teams: {}", dtos.size());
        return dtos.stream().map(dto -> teamMapper.fromDTO(dto)).collect(toList());
    }

    @Override
    public Collection<Tournament> fetchLeagues(Country country) {
        WebTarget target = getBaseTarget().path("/soccer/leagues");
        LOGGER.debug("Connecting to {}", target);
        String countryId = country.getRemoteKey();
        Invocation.Builder request = target.queryParam("country_id", countryId)
                .request(MediaType.APPLICATION_JSON)
                .header("apikey", environmentConfig.getAPi1Apikey());
        Response response = request.get();
        LOGGER.debug("Response status: {}", response.getStatus());
        if (response.getStatus() != 200) {
            throw new GameonRuntimeException("Unable to connect to target %s, got response status %d", target, response.getStatus());
        }

        String json = response.readEntity(String.class);
        LOGGER.debug("Response JSON: {}", json);

        EnvelopeDTO<LeaguesQueryDTO, Map<Integer, LeagueDTO>> envelopeDTO = jsonb.fromJson(json, new EnvelopeDTO<LeaguesQueryDTO, Map<Integer, LeagueDTO>>() {
        }.getClass().getGenericSuperclass());

        Collection<LeagueDTO> dtos = envelopeDTO.getData().values();

        LOGGER.debug("Number of leagues: {}", dtos.size());
        return dtos.stream().map(dto -> tournamentMapper.fromDTO(dto)).collect(toList());
    }

    @Override
    public Collection<Country> fetchCountry() {
        WebTarget target = getBaseTarget().path("/soccer/countries");
        LOGGER.debug("Connecting to {}", target);
        Invocation.Builder request = target
                .request(MediaType.APPLICATION_JSON)
                .header("apikey", environmentConfig.getAPi1Apikey());
        Response response = request.get();
        LOGGER.debug("Response status: {}", response.getStatus());

        String json = response.readEntity(String.class);
        LOGGER.debug("Response JSON: {}", json);

        EnvelopeDTO<CountriesQueryDTO, Collection<CountryDTO>> envelopeDTO = jsonb.fromJson(json, new EnvelopeDTO<CountriesQueryDTO, Collection<CountryDTO>>() {
        }.getClass().getGenericSuperclass());

        Collection<CountryDTO> dtos = envelopeDTO.getData();

        LOGGER.debug("Number of countries: {}", dtos.size());
        return dtos.stream().map(dto -> countryMapper.fromDTO(dto)).collect(toList());
    }

    private <T> GenericType<Collection<T>> collectionType(Class<T> clazz) {
        return new GenericType<Collection<T>>() {
        };
    }

    private WebTarget getBaseTarget() {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.property(LoggingFeature.LOGGING_FEATURE_VERBOSITY_CLIENT, LoggingFeature.Verbosity.HEADERS_ONLY);
        return ClientBuilder.newClient(clientConfig).target(environmentConfig.getApi1BaseUrl());
    }

}
