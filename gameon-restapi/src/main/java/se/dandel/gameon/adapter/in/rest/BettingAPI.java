package se.dandel.gameon.adapter.in.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.dandel.gameon.domain.model.bet.BettingGameUser;
import se.dandel.gameon.domain.repository.BettingGameRepository;

@RestController("/betting")
public class BettingAPI {

    @Autowired
    private BettingGameRepository bettingGameRepository;

    @Autowired
    private BettingGameUserModelMapper mapper;

    @GetMapping("/game/user/{id}")
    public BettingGameUserModel get(@RequestParam("id") long id) {
        BettingGameUser bettingGame = bettingGameRepository.findByBettingGameUser(id);
        return mapper.toModel(bettingGame);
    }

}
