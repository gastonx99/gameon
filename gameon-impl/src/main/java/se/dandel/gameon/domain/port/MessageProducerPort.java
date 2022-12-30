package se.dandel.gameon.domain.port;

import se.dandel.gameon.domain.model.*;

public interface MessageProducerPort {
    void produceMessage(Team team);

    void produceMessage(Tournament tournament);

    void produceMessage(Season season);

    void produceMessage(Match match);

    void produceMessage(Country country);
}
