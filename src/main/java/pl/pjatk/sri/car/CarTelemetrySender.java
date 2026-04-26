package pl.pjatk.sri.car;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.pjatk.sri.config.Destinations;
import pl.pjatk.sri.model.CarTelemetry;

import java.util.Random;

@Component
public class CarTelemetrySender {

    private final JmsTemplate topicJmsTemplate;
    private final Random random = new Random();

    public CarTelemetrySender(@Qualifier("topicJmsTemplate") JmsTemplate topicJmsTemplate) {
        this.topicJmsTemplate = topicJmsTemplate;
    }

    @Scheduled(fixedDelay = 10000)
    public void sendTelemetry() {
        CarTelemetry telemetry = new CarTelemetry(
            simulateEngineTemp(),
            simulateOilPressure(),
            simulateTirePressure(),
            simulateTirePressure(),
            simulateTirePressure(),
            simulateTirePressure()
        );

        topicJmsTemplate.convertAndSend(Destinations.CAR_TELEMETRY_TOPIC, telemetry);
        System.out.println("[CAR] Telemetry sent: " + telemetry);
    }

    private double simulateEngineTemp() {
        // Mostly 85-115, with 20% chance of hitting warning/critical zone
        double base = 85 + random.nextDouble() * 30;
        if (random.nextDouble() < 0.15) base += 40; // warning zone
        if (random.nextDouble() < 0.05) base += 25; // critical zone
        return Math.round(base * 10.0) / 10.0;
    }

    private double simulateOilPressure() {
        // Normal 2.5-4.0, with occasional drop below 2.0
        double base = 2.5 + random.nextDouble() * 1.5;
        if (random.nextDouble() < 0.15) base -= 1.0;
        return Math.round(base * 100.0) / 100.0;
    }

    private double simulateTirePressure() {
        // Normal 2.0-2.4, with occasional drop below 1.8
        double base = 2.0 + random.nextDouble() * 0.4;
        if (random.nextDouble() < 0.1) base -= 0.4;
        return Math.round(base * 100.0) / 100.0;
    }
}
