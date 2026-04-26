package pl.pjatk.sri.car;

import javax.jms.*;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import pl.pjatk.sri.config.Destinations;
import pl.pjatk.sri.model.PitStopDecision;
import pl.pjatk.sri.model.PitStopRequest;

import java.util.UUID;

@Service
public class PitStopService {

    private static final long REPLY_TIMEOUT_MS = 5000;

    private final JmsTemplate jmsTemplate;

    public PitStopService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    /**
     * Request-Reply: sends a PitStopRequest and blocks until TeamManagerService responds
     * (or the 5-second timeout elapses).
     */
    public PitStopDecision requestPitStop(String reason, int lapNumber) {
        PitStopRequest request = new PitStopRequest(reason, lapNumber, UUID.randomUUID().toString());
        System.out.println("[CAR] Sending pit-stop request: " + request);

        return jmsTemplate.execute(session -> {
            TemporaryQueue replyQueue = session.createTemporaryQueue();
            Queue requestQueue = session.createQueue(Destinations.PITSTOP_REQUEST_QUEUE);

            try (MessageProducer producer = session.createProducer(requestQueue)) {
                ObjectMessage msg = session.createObjectMessage(request);
                msg.setJMSReplyTo(replyQueue);
                producer.send(msg);
            }

            try (MessageConsumer consumer = session.createConsumer(replyQueue)) {
                Message response = consumer.receive(REPLY_TIMEOUT_MS);
                if (response instanceof ObjectMessage) {
                    ObjectMessage objMsg = (ObjectMessage) response;
                    return (PitStopDecision) objMsg.getObject();
                }
                System.out.println("[CAR] No response received within timeout.");
                return null;
            }
        }, true); // startConnection=true is required for consumer.receive() to work
    }
}
