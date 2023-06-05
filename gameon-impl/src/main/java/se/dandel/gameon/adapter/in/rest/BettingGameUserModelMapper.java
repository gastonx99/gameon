package se.dandel.gameon.adapter.in.rest;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.mapstruct.*;
import se.dandel.gameon.domain.model.Match;
import se.dandel.gameon.domain.model.Team;
import se.dandel.gameon.domain.model.bet.Bet;
import se.dandel.gameon.domain.model.bet.BettingGameUser;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static java.util.Comparator.comparing;
import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.*;

@Mapper(
        componentModel = "spring",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT
)
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

    @AfterMapping
    default void setStageGroupRoundIndexes(@MappingTarget BettingGameUserModel target) {
        Map<String, List<BettingGameUserModel.BetModel>> perStage = setIndex(target.bets, bet -> bet.match.stage, (bet, index) -> bet.match.stageIndex = index);
        perStage.values().forEach(listOfBetsInStage -> {
            Map<String, List<BettingGameUserModel.BetModel>> perGroup = setIndex(listOfBetsInStage, bet -> bet.match.group, (bet, index) -> bet.match.groupIndex = index);
            perGroup.values().forEach(listOfBetsInGroup -> {
                setIndex(listOfBetsInGroup, bet -> bet.match.round, (bet1, index) -> bet1.match.roundIndex = index);
            });
        });
        Comparator<? super BettingGameUserModel.BetModel> comparator = (Comparator<BettingGameUserModel.BetModel>) (o1, o2) -> {
            CompareToBuilder compareToBuilder = new CompareToBuilder();
            compareToBuilder.append(o1.match.stageIndex, o2.match.stageIndex);
            compareToBuilder.append(o1.match.groupIndex, o2.match.groupIndex);
            compareToBuilder.append(o1.match.roundIndex, o2.match.roundIndex);
            return compareToBuilder.toComparison();
        };
        target.bets = target.bets.stream().sorted(comparator).collect(toList());
    }

    private Map<String, List<BettingGameUserModel.BetModel>> setIndex(Collection<BettingGameUserModel.BetModel> listOfBets, Function<BettingGameUserModel.BetModel, String> groupingFunction, BiConsumer<BettingGameUserModel.BetModel, Integer> betModelConsumer) {
        AtomicInteger groupIndex = new AtomicInteger(0);
        Map<String, List<BettingGameUserModel.BetModel>> perGroup = listOfBets.stream().collect(groupingBy(groupingFunction));
        Map<String, Optional<BettingGameUserModel.BetModel>> perGroupMinimum = listOfBets.stream().collect(groupingBy(groupingFunction, minBy(comparing(bet -> bet.match.matchStart))));
        perGroupMinimum.entrySet().stream().sorted(comparingByValue(comparing(bet -> bet.get().match.matchStart))).forEach(entry -> {
            String key = entry.getKey();
            int index = groupIndex.getAndIncrement();
            perGroup.get(key).forEach(bet -> {
                betModelConsumer.accept(bet, index);

            });
        });
        return perGroup;
    }
}
