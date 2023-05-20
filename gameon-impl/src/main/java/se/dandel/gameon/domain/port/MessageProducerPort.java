package se.dandel.gameon.domain.port;

import org.springframework.stereotype.Component;
import se.dandel.gameon.domain.model.*;

@Component
public interface MessageProducerPort {
    void produceMessage(Team team);

    void produceMessage(Tournament tournament);

    void produceMessage(Season season);

    void produceMessage(Match match);

    void produceMessage(Country country);
}
