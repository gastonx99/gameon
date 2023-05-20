package se.dandel.gameon.adapter.out.message;

import jakarta.jms.JMSException;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;
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
        jmsTemplate.send(qTeam, session -> getTextMessage(team, session));
    }


    @Override
    public void produceMessage(Tournament tournament) {
        jmsTemplate.send(qTournament, session -> getTextMessage(tournament, session));
    }

    @Override
    public void produceMessage(Season season) {
        jmsTemplate.send(qSeason, session -> getTextMessage(season, session));
    }

    @Override
    public void produceMessage(Match match) {
        jmsTemplate.send(qMatch, session -> getTextMessage(match, session));
    }

    @Override
    public void produceMessage(Country country) {
        jmsTemplate.send(qCountry, session -> getTextMessage(country, session));
    }

    private static TextMessage getTextMessage(Object object, Session session) throws JMSException {
        TextMessage textMessage = session.createTextMessage(object.toString());
        LOGGER.atInfo().log("Sending message: {}".formatted(object));
        return textMessage;
    }
}
