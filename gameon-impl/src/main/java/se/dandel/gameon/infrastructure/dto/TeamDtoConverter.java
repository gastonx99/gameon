package se.dandel.gameon.infrastructure.dto;

import org.mapstruct.Mapper;

import se.dandel.gameon.domain.model.Team;

@Mapper(componentModel = "cdi")
public interface TeamDtoConverter {
    TeamDto convert(Team source);

    TeamDto clone(TeamDto source);
}