package se.dandel.gameon.adapter.in.rest;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import se.dandel.gameon.domain.model.bet.BettingGameUser;

import java.util.Collection;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BettingGameUserWidgetModelMapper {

    default BettingGameUserWidgetModel toModel(Collection<BettingGameUser> bettingGameUsers) {
        BettingGameUserWidgetModel model = new BettingGameUserWidgetModel();
        model.games = bettingGameUsers.stream().map(this::toModel).collect(Collectors.toList());
        return model;
    }

    @Mapping(source = "bettingGame.name", target = "name")
    BettingGameUserWidgetModel.BettingGameUserModel toModel(BettingGameUser bettingGameUser);

}
