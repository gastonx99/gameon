package se.dandel.gameon.adapter.in.rest;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import se.dandel.gameon.domain.model.Team;

@Mapper(componentModel = "cdi")
public interface TeamModelMapper {
    @Mapping(source = "country.name", target = "countryName")
    @Mapping(source = "country.countryCode", target = "countryCode")
    @Mapping(source = "country.continent", target = "countryContinent")
    TeamModel toModel(Team team);

}
