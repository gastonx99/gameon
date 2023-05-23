package se.dandel.gameon.adapter.mdb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;

public class TeamMDB {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @JmsListener(destination = "gameon.queue.team")
    public void onMessage(String team) {
        LOGGER.atInfo().log("Received message {}".formatted(team));
    }
}
