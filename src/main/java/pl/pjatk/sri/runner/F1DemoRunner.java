package pl.pjatk.sri.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.pjatk.sri.car.PitStopService;
import pl.pjatk.sri.model.PitStopDecision;

@Component
public class F1DemoRunner implements CommandLineRunner {

    private final PitStopService pitStopService;

    public F1DemoRunner(PitStopService pitStopService) {
        this.pitStopService = pitStopService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Give JMS listener containers a moment to fully start
        Thread.sleep(500);

        System.out.println("\n=== F1 PIT-STOP REQUEST DEMO ===");
        PitStopDecision decision = pitStopService.requestPitStop("Opony zużyte", 34);

        if (decision != null) {
            System.out.println("[DEMO] Response received: " + decision);
            System.out.println("[DEMO] Pit-stop " + (decision.isAccepted() ? "ACCEPTED" : "REJECTED")
                + " – " + decision.getJustification());
        } else {
            System.out.println("[DEMO] No response from team manager (timeout).");
        }
        System.out.println("================================\n");
    }
}
