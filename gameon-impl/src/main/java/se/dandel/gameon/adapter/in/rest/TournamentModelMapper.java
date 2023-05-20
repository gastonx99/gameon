package se.dandel.gameon.adapter.in.rest;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import se.dandel.gameon.domain.model.Match;
import se.dandel.gameon.domain.model.Season;
import se.dandel.gameon.domain.model.Team;
import se.dandel.gameon.domain.model.Tournament;

@Mapper(componentModel = "spring")
public interface TournamentModelMapper {
    @Mapping(source = "country.name", target = "countryName")
    @Mapping(source = "country.countryCode", target = "countryCode")
    @Mapping(source = "country.continent", target = "countryContinent")
    TournamentModel toModel(Tournament tournament);

    TournamentModel.SeasonModel toModel(Season season);

    TournamentModel.MatchModel toMatch(Match match);

    @Mapping(source = "country.name", target = "countryName")
    @Mapping(source = "country.countryCode", target = "countryCode")
    @Mapping(source = "country.continent", target = "countryContinent")
    TournamentModel.TeamModel toModel(Team team);
}
