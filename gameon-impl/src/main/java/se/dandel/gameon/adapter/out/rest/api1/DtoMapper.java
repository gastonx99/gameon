package se.dandel.gameon.adapter.out.rest.api1;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import se.dandel.gameon.domain.model.*;

@Mapper(componentModel = "cdi")
public interface DtoMapper {
    @Mapping(source = "countryId", target = "remoteKey.remoteKey")
    Country fromDTO(CountryDTO source);

    @Mapping(source = "teamId", target = "remoteKey.remoteKey")
    Team fromDTO(TeamDTO source);

    @Mapping(target = "type", constant = "LEAGUE")
    @Mapping(source = "leagueId", target = "remoteKey.remoteKey")
    Tournament fromDTO(LeagueDTO source);

    @Mapping(source = "seasonId", target = "remoteKey.remoteKey")
    Season fromDTO(SeasonDTO source);

    @Mapping(source = "matchId", target = "remoteKey.remoteKey")
    @Mapping(target = "matchStart", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "finalScore.home", source = "stats.homeScore")
    @Mapping(target = "finalScore.away", source = "stats.awayScore")
    Match fromDTO(MatchDTO source);
}
