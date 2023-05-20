package se.dandel.gameon.adapter.out.rest.api1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import se.dandel.gameon.domain.model.*;
import se.dandel.gameon.domain.port.Api1Port;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Component
public class Api1PortAdapter implements Api1Port {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    static final String PATH_SOCCER_COUNTRIES = "/soccer/countries";

    static final String PATH_SOCCER_TEAMS = "/soccer/teams";

    static final String PATH_SOCCER_LEAGUES = "/soccer/leagues";

    static final String PATH_SOCCER_SEASONS = "/soccer/seasons";

    static final String PATH_SOCCER_MATCHES = "/soccer/matches";

    @Value("se.dandel.gameon.api1.base.url")
    private String baseUrl;

    @Value("se.dandel.gameon.api1.base.apikey")
    private String apiKey;

    @Autowired
    private DtoMapper dtoMapper;


    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Override
    public Collection<Country> fetchCountry() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Collection<CountryDTO>> response = restTemplate.exchange(baseUrl + PATH_SOCCER_COUNTRIES, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });

        Collection<CountryDTO> dtos = response.getBody();

        LOGGER.atDebug().log("Number of countries: {}", dtos.size());
        return dtos.stream().map(dto -> dtoMapper.fromDTO(dto)).collect(toList());
    }

    @Override
    public Collection<Team> fetchTeams(RemoteKey remoteKeyCountry) {
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(baseUrl + PATH_SOCCER_TEAMS).queryParam("country_id", "{country_id}").encode().toUriString();
        Map<String, String> params = new HashMap<>();
        params.put("country_id", remoteKeyCountry.getRemoteKey());

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Collection<TeamDTO>> response = restTemplate.exchange(urlTemplate, HttpMethod.GET, getHeaders(), new ParameterizedTypeReference<>() {
        }, params);

        Collection<TeamDTO> dtos = response.getBody();
        LOGGER.atDebug().log("Number of teams: {}", dtos.size());
        return dtos.stream().map(dto -> dtoMapper.fromDTO(dto)).collect(toList());
    }


    @Override
    public Collection<Tournament> fetchLeagues(Optional<RemoteKey> remoteKeyCountry) {
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(baseUrl + PATH_SOCCER_LEAGUES).queryParam("country_id", "{country_id}").encode().toUriString();
        Map<String, String> params = new HashMap<>();
        params.put("country_id", remoteKeyCountry.isEmpty() ? "" : remoteKeyCountry.get().getRemoteKey());

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Collection<LeagueDTO>> response = restTemplate.exchange(urlTemplate, HttpMethod.GET, getHeaders(), new ParameterizedTypeReference<>() {
        }, params);

        Collection<LeagueDTO> dtos = response.getBody();
        LOGGER.atDebug().log("Number of leagues: {}", dtos.size());
        return dtos.stream().map(dto -> dtoMapper.fromDTO(dto)).collect(toList());
    }

    @Override
    public Collection<Season> fetchSeasons(RemoteKey remoteKeyTournament) {
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(baseUrl + PATH_SOCCER_LEAGUES).queryParam("country_id", "{country_id}").encode().toUriString();
        Map<String, String> params = new HashMap<>();
        params.put("league_id", remoteKeyTournament.getRemoteKey());

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Collection<SeasonDTO>> response = restTemplate.exchange(urlTemplate, HttpMethod.GET, getHeaders(), new ParameterizedTypeReference<>() {
        }, params);

        Collection<SeasonDTO> dtos = response.getBody();
        LOGGER.atDebug().log("Number of seasons: {}", dtos.size());
        return dtos.stream().map(dto -> dtoMapper.fromDTO(dto)).collect(toList());
    }

    @Override
    public Collection<Match> fetchMatches(RemoteKey remoteKeySeason) {
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(baseUrl + PATH_SOCCER_MATCHES).queryParam("season_id", "{season_id}").encode().toUriString();
        Map<String, String> params = new HashMap<>();
        params.put("season_id", remoteKeySeason.getRemoteKey());

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Collection<MatchDTO>> response = restTemplate.exchange(urlTemplate, HttpMethod.GET, getHeaders(), new ParameterizedTypeReference<>() {
        }, params);

        Collection<MatchDTO> dtos = response.getBody();
        LOGGER.atDebug().log("Number of seasons: {}", dtos.size());
        return dtos.stream().map(dto -> dtoMapper.fromDTO(dto)).collect(toList());

    }

    private static HttpEntity<?> getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        return entity;
    }
}
