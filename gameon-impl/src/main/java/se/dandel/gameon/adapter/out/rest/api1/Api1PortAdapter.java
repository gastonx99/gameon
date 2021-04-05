package se.dandel.gameon.adapter.out.rest.api1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.dandel.gameon.domain.model.Country;
import se.dandel.gameon.domain.model.Season;
import se.dandel.gameon.domain.model.Team;
import se.dandel.gameon.domain.model.Tournament;
import se.dandel.gameon.domain.port.Api1Port;

import javax.inject.Inject;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class Api1PortAdapter implements Api1Port {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    static final String PATH_SOCCER_COUNTRIES = "/soccer/countries";

    static final String PATH_SOCCER_TEAMS = "/soccer/teams";

    static final String PATH_SOCCER_LEAGUES = "/soccer/leagues";

    static final String PATH_SOCCER_SEASONS = "/soccer/seasons";

    static final Type ENVELOPE_COUNTRIES = new EnvelopeDTO<Collection<CountryDTO>>() {
    }.getClass().getGenericSuperclass();

    static final Type ENVELOPE_TEAMS = new EnvelopeDTO<Collection<TeamDTO>>() {
    }.getClass().getGenericSuperclass();

    static final Type ENVELOPE_LEAGUES = new EnvelopeDTO<Map<Integer, LeagueDTO>>() {
    }.getClass().getGenericSuperclass();

    static final Type ENVELOPE_SEASONS = new EnvelopeDTO<Collection<SeasonDTO>>() {
    }.getClass().getGenericSuperclass();


    @Inject
    private DtoMapper dtoMapper;

    @Inject
    private Api1PortInvoker invoker;

    @Override
    public Collection<Country> fetchCountry() {
        Collection<CountryDTO> dtos = invoker.invoke(ENVELOPE_COUNTRIES, PATH_SOCCER_COUNTRIES, Collections.emptyMap());
        LOGGER.debug("Number of countries: {}", dtos.size());
        return dtos.stream().map(dto -> dtoMapper.fromDTO(dto)).collect(toList());
    }

    @Override
    public Collection<Team> fetchTeams(Country country) {
        Collection<TeamDTO> dtos = invoker.invoke(ENVELOPE_TEAMS, PATH_SOCCER_TEAMS, Collections.singletonMap("country_id", country.getRemoteKey().getRemoteKey()));
        LOGGER.debug("Number of teams: {}", dtos.size());
        return dtos.stream().map(dto -> dtoMapper.fromDTO(dto)).collect(toList());
    }

    @Override
    public Collection<Tournament> fetchLeagues(Country country) {
        Collection<LeagueDTO> dtos = invoker.invoke(ENVELOPE_LEAGUES, PATH_SOCCER_LEAGUES, Collections.singletonMap("country_id", country.getRemoteKey().getRemoteKey()));
        LOGGER.debug("Number of leagues: {}", dtos.size());
        return dtos.stream().map(dto -> dtoMapper.fromDTO(dto)).collect(toList());
    }

    @Override
    public Collection<Season> fetchSeasons(Tournament tournament) {
        Collection<SeasonDTO> dtos = invoker.invoke(ENVELOPE_SEASONS, PATH_SOCCER_SEASONS, Collections.singletonMap("league_id", tournament.getRemoteKey().getRemoteKey()));
        LOGGER.debug("Number of seasons: {}", dtos.size());
        return dtos.stream().map(dto -> dtoMapper.fromDTO(dto)).collect(toList());
    }

}
