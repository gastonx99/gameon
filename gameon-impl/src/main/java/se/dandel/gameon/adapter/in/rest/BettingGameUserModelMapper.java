package se.dandel.gameon.adapter.in.rest;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import se.dandel.gameon.domain.model.Match;
import se.dandel.gameon.domain.model.Team;
import se.dandel.gameon.domain.model.bet.Bet;
import se.dandel.gameon.domain.model.bet.BettingGameUser;

@Mapper(componentModel = "cdi")
public interface BettingGameUserModelMapper {
    @Mapping(source = "pk", target = "pk")
    @Mapping(source = "bettingGame.season.name", target = "season")
    @Mapping(source = "bettingGame.season.tournament.name", target = "tournament")
    @Mapping(source = "bettingGame.name", target = "name")
    @Mapping(source = "bettingGame.owner.username", target = "bettingGameOwner")
    BettingGameUserModel toModel(BettingGameUser source);

    @Mapping(source = "score.home", target = "homeScore")
    @Mapping(source = "score.away", target = "awayScore")
    BettingGameUserModel.BetModel toModel(Bet source);

    @Mapping(source = "finalScore.home", target = "finalHomeScore")
    @Mapping(source = "finalScore.away", target = "finalAwayScore")
    @Mapping(source = "matchStart", target = "matchStart", dateFormat = "yyyy-MM-dd HH:mm")
    BettingGameUserModel.MatchModel toModel(Match source);

    @Mapping(source = "country.name", target = "countryName")
    @Mapping(source = "country.countryCode", target = "countryCode")
    BettingGameUserModel.TeamModel toModel(Team source);
}
