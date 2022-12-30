package se.dandel.gameon.adapter.messaging;

import javax.annotation.Resource;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.jms.JMSContext;
import javax.jms.Queue;

public class MessageDestinationProducer {

    @Resource
    private JMSContext jmsContext;

    @Resource(lookup = "jms/Team.Q")
    private static Queue teamQueue;

    @Resource(lookup = "jms/Tournament.Q")
    private static Queue tournamentQueue;

    @Resource(lookup = "jms/Season.Q")
    private static Queue seasonQueue;

    @Resource(lookup = "jms/Match.Q")
    private static Queue matchQueue;

    @Resource(lookup = "jms/Country.Q")
    private static Queue countryQueue;

    @Produces
    public JMSContext getJmsContext() {
        return jmsContext;
    }

    @Produces
    @Named("Team.Q")
    public Queue teamDestination() {
        return teamQueue;
    }

    @Produces
    @Named("Tournament.Q")
    public Queue tournamenDestination() {
        return tournamentQueue;
    }

    @Produces
    @Named("Season.Q")
    public Queue seasonDestination() {
        return seasonQueue;
    }

    @Produces
    @Named("Match.Q")
    public Queue matchDestination() {
        return matchQueue;
    }

    @Produces
    @Named("Country.Q")
    public Queue countryDestination() {
        return countryQueue;
    }
}
