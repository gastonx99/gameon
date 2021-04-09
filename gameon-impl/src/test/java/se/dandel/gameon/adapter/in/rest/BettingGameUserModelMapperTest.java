package se.dandel.gameon.adapter.in.rest;

import org.apache.commons.lang3.builder.RecursiveToStringStyle;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.dandel.gameon.domain.model.TestBettingGameFactory;
import se.dandel.gameon.domain.model.bet.BettingGame;
import se.dandel.gameon.domain.model.bet.BettingGameUser;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class BettingGameUserModelMapperTest {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private BettingGameUserModelMapper mapper = new BettingGameUserModelMapperImpl();

    @Test
    void toBettingGameUserModel() {
        // Given
        BettingGame bettingGame = TestBettingGameFactory.createBettingGameEuro2021();
        BettingGameUser expected = bettingGame.getParticipants().iterator().next();

        // When
        BettingGameUserModel actual = mapper.toModel(expected);
        LOGGER.debug("BettingGameUserModel: {}", ReflectionToStringBuilder.toString(actual, new RecursiveToStringStyle()));
        Collection<BettingGameUserModel.BetModel> actualBets = actual.bets;

        // Then
        actualBets.forEach(actualBet -> {
            assertNotNull(actualBet.match.homeTeam.name, "Team name should be empty string when team is missing");
            assertNotNull(actualBet.match.awayTeam.name, "Team name should be empty string when team is missing");
        });
    }
}