package se.dandel.gameon.adapter.out.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import se.dandel.gameon.domain.model.*;
import se.dandel.gameon.domain.port.MessageProducerPort;

@Component
public class MessageProducerAdapter implements MessageProducerPort {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageProducerAdapter.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("gameon.queue.team")
    private String qTeam;

    @Value("gameon.queue.tournament")
    private String qTournament;

    @Value("gameon.queue.season")
    private String qSeason;

    @Value("gameon.queue.match")
    private String qMatch;

    @Value("gameon.queue.country")
    private String qCountry;

    @Override
    public void produceMessage(Team team) {
        jmsTemplate.convertAndSend(qTeam, team.toString());
    }

    @Override
    public void produceMessage(Tournament tournament) {
        jmsTemplate.convertAndSend(qTournament, tournament.toString());
    }

    @Override
    public void produceMessage(Season season) {
        jmsTemplate.convertAndSend(qSeason, season.toString());
    }

    @Override
    public void produceMessage(Match match) {
        jmsTemplate.convertAndSend(qMatch, match.toString());
    }

    @Override
    public void produceMessage(Country country) {
        jmsTemplate.convertAndSend(qCountry, country.toString());
    }

}
