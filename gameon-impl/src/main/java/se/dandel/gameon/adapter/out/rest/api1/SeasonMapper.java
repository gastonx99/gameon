package se.dandel.gameon.adapter.out.rest.api1;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import se.dandel.gameon.domain.model.Season;

@Mapper(componentModel = "cdi")
public interface SeasonMapper {
    @Mapping(source = "seasonId", target = "remoteKey.remoteKey")
    Season fromDTO(SeasonDTO source);
}
