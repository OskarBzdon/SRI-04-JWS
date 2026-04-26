package pl.pjatk.sri.pitstop;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import pl.pjatk.sri.config.Destinations;
import pl.pjatk.sri.model.CarTelemetry;

@Component
public class RaceLoggerListener {

    @JmsListener(destination = Destinations.CAR_TELEMETRY_TOPIC, containerFactory = "topicListenerFactory")
    public void onTelemetry(CarTelemetry telemetry) {
        System.out.println("[LOGGER] " + telemetry);
    }
}
