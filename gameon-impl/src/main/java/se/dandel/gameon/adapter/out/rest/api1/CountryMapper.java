package se.dandel.gameon.adapter.out.rest.api1;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import se.dandel.gameon.domain.model.Country;

@Mapper(componentModel = "cdi")
public interface CountryMapper {
    @Mapping(source = "countryId", target = "remoteKey.remoteKey")
    Country fromDTO(CountryDTO source);
}
