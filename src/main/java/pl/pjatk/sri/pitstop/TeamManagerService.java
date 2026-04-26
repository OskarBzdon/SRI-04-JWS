package pl.pjatk.sri.pitstop;

import jakarta.jms.Message;
import jakarta.jms.ObjectMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import pl.pjatk.sri.config.Destinations;
import pl.pjatk.sri.model.PitStopDecision;
import pl.pjatk.sri.model.PitStopRequest;

import java.util.Random;

@Component
public class TeamManagerService {

    private final JmsTemplate jmsTemplate;
    private final Random random = new Random();

    public TeamManagerService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @JmsListener(destination = Destinations.PITSTOP_REQUEST_QUEUE)
    public void onPitStopRequest(Message message) {
        try {
            PitStopRequest request = (PitStopRequest) ((ObjectMessage) message).getObject();
            System.out.println("[TEAM MANAGER] Received: " + request);

            boolean accepted = random.nextBoolean();
            String justification = accepted
                ? "Okno pit-stop gotowe, mechanicy na stanowiskach."
                : "Za duży ruch na torze, proszę kontynuować jazdę.";

            PitStopDecision decision = new PitStopDecision(accepted, justification, request.getRequestId());
            System.out.println("[TEAM MANAGER] Decision: " + decision);

            jmsTemplate.convertAndSend(message.getJMSReplyTo(), decision);

        } catch (Exception e) {
            System.err.println("[TEAM MANAGER] Error processing pit-stop request: " + e.getMessage());
        }
    }
}
