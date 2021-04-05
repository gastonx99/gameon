package se.dandel.gameon.adapter.out.rest.api1;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import se.dandel.gameon.domain.model.Country;
import se.dandel.gameon.domain.model.Season;
import se.dandel.gameon.domain.model.Team;
import se.dandel.gameon.domain.model.Tournament;

@Mapper(componentModel = "cdi")
public interface DtoMapper {
    @Mapping(source = "seasonId", target = "remoteKey.remoteKey")
    Season fromDTO(SeasonDTO source);

    @Mapping(source = "countryId", target = "remoteKey.remoteKey")
    Country fromDTO(CountryDTO source);

    @Mapping(source = "teamId", target = "remoteKey.remoteKey")
    Team fromDTO(TeamDTO source);

    @Mapping(target = "type", constant = "LEAGUE")
    @Mapping(source = "leagueId", target = "remoteKey.remoteKey")
    Tournament fromDTO(LeagueDTO source);
}
