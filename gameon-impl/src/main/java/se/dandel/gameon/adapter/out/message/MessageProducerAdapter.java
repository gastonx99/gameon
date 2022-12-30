package se.dandel.gameon.adapter.out.message;

import se.dandel.gameon.domain.port.MessageProducerPort;
import se.dandel.gameon.domain.model.*;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;

public class MessageProducerAdapter implements MessageProducerPort {

    @Inject
    @Named("Team.Q")
    private Queue teamQueue;

    @Inject
    @Named("Tournament.Q")
    private Queue tournamentQueue;

    @Inject
    @Named("Season.Q")
    private Queue seasonQueue;

    @Inject
    @Named("Match.Q")
    private Queue matchQueue;

    @Inject
    @Named("Country.Q")
    private Queue countryQueue;

    @Inject
    private JMSContext jmsContext;

    @Override
    public void produceMessage(Team team) {
        JMSProducer producer = jmsContext.createProducer();
        producer.send(teamQueue, team.toString());
    }

    @Override
    public void produceMessage(Tournament tournament) {
        JMSProducer producer = jmsContext.createProducer();
        producer.send(tournamentQueue, tournament.toString());
    }

    @Override
    public void produceMessage(Season season) {
        JMSProducer producer = jmsContext.createProducer();
        producer.send(seasonQueue, season.toString());
    }

    @Override
    public void produceMessage(Match match) {
        JMSProducer producer = jmsContext.createProducer();
        producer.send(matchQueue, match.toString());
    }

    @Override
    public void produceMessage(Country country) {
        JMSProducer producer = jmsContext.createProducer();
        producer.send(countryQueue, country.toString());
    }
}
