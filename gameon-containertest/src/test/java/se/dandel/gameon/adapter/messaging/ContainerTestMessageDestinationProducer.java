package se.dandel.gameon.adapter.messaging;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.Specializes;
import javax.inject.Named;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Specializes
@ApplicationScoped
public class ContainerTestMessageDestinationProducer extends MessageDestinationProducer {

    private JMSProducer jmsProducer = mock(JMSProducer.class);
    private JMSContext jmsContext = mock(JMSContext.class);
    private Queue teamDestination = mock(Queue.class);
    private Queue tournamenDestination = mock(Queue.class);
    private Queue seasonDestination = mock(Queue.class);
    private Queue matchDestination = mock(Queue.class);
    private Queue countryDestination = mock(Queue.class);

    public ContainerTestMessageDestinationProducer() {
        when(jmsContext.createProducer()).thenReturn(jmsProducer);
    }

    @Override
    @Produces
    public JMSContext getJmsContext() {
        return jmsContext;
    }

    @Override
    @Produces
    @Named("Team.Q")
    public Queue teamDestination() {
        return teamDestination;
    }

    @Override
    @Produces
    @Named("Tournament.Q")
    public Queue tournamenDestination() {
        return tournamenDestination;
    }

    @Override
    @Produces
    @Named("Season.Q")
    public Queue seasonDestination() {
        return seasonDestination;
    }

    @Override
    @Produces
    @Named("Match.Q")
    public Queue matchDestination() {
        return matchDestination;
    }

    @Override
    @Produces
    @Named("Country.Q")
    public Queue countryDestination() {
        return countryDestination;
    }

    @Produces
    public JMSProducer jmsProducer() {
        return jmsProducer;
    }
}
