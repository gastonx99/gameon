package se.dandel.gameon.adapter.out.rest.api1;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import se.dandel.gameon.domain.model.Team;

@Mapper(componentModel = "cdi")
public interface TeamMapper {

    @Mapping(source = "shortCode", target = "key")
    Team fromDTO(TeamDTO source);
}