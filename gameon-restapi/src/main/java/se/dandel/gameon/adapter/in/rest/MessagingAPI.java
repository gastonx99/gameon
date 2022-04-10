package se.dandel.gameon.adapter.in.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.Resource;
import jakarta.inject.Inject;
import jakarta.jms.*;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/jms-client")
public class MessagingAPI {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Resource(lookup = "java:/jms/queue/Team.Q")
    private static Queue queue;

    @Inject
    @JMSConnectionFactory("java:/jms/cf/QueueConnectionFactory")
    private JMSContext context;

    @GET
    @Path("/send")
    @Produces(MediaType.TEXT_PLAIN)
    public Response send() {
        LOGGER.info("Sending message using context {}", context);
        try {
            TextMessage textMessage = context.createTextMessage();
            textMessage.setText("Howdy pardner");
            context.createProducer().send(queue, textMessage);
            return Response.ok("Message sent").build();
        } catch (JMSException e) {
            LOGGER.error(e.getMessage(), e);
            return Response.ok("Message failed: " + e.getMessage()).build();
        }
    }

}
