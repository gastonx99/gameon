package se.dandel.gameon.adapter.out.rest.api1;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import se.dandel.gameon.domain.model.Tournament;

@Mapper(componentModel = "cdi")
public interface TournamentMapper {
    @Mapping(target = "type", constant = "LEAGUE")
    @Mapping(source = "leagueId", target = "remoteKey.remoteKey")
    Tournament fromDTO(LeagueDTO source);
}
