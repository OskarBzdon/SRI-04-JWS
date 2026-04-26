package pl.pjatk.sri.pitstop;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import pl.pjatk.sri.config.Destinations;
import pl.pjatk.sri.model.CarTelemetry;
import pl.pjatk.sri.model.DriverAlert;
import pl.pjatk.sri.model.MechanicAlert;

@Component
public class CarMonitorListener {

    private static final double ENGINE_TEMP_WARNING  = 120.0;
    private static final double ENGINE_TEMP_CRITICAL = 140.0;
    private static final double OIL_PRESSURE_MIN     = 2.0;
    private static final double TIRE_PRESSURE_MIN    = 1.8;

    private final JmsTemplate jmsTemplate;

    public CarMonitorListener(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @JmsListener(destination = Destinations.CAR_TELEMETRY_TOPIC, containerFactory = "topicListenerFactory")
    public void onTelemetry(CarTelemetry t) {
        checkEngineTemp(t.getEngineTemp());
        checkOilPressure(t.getOilPressure());
        checkTirePressure("FL", t.getTirePressureFL());
        checkTirePressure("FR", t.getTirePressureFR());
        checkTirePressure("RL", t.getTirePressureRL());
        checkTirePressure("RR", t.getTirePressureRR());
    }

    private void checkEngineTemp(double temp) {
        if (temp >= ENGINE_TEMP_CRITICAL) {
            sendMechanicAlert("Krytyczna temperatura silnika: " + temp + "°C", "CRITICAL");
            sendDriverAlert("UWAGA: Krytyczna awaria silnika – przygotuj się do bezpiecznego zjazdu!");
        } else if (temp >= ENGINE_TEMP_WARNING) {
            sendMechanicAlert("Wysoka temperatura silnika: " + temp + "°C", "WARNING");
        }
    }

    private void checkOilPressure(double pressure) {
        if (pressure < OIL_PRESSURE_MIN) {
            sendMechanicAlert("Niskie ciśnienie oleju: " + pressure + " bar", "WARNING");
        }
    }

    private void checkTirePressure(String position, double pressure) {
        if (pressure < TIRE_PRESSURE_MIN) {
            sendMechanicAlert("Niskie ciśnienie opony " + position + ": " + pressure + " bar", "WARNING");
        }
    }

    private void sendMechanicAlert(String problem, String severity) {
        MechanicAlert alert = new MechanicAlert(problem, severity);
        jmsTemplate.convertAndSend(Destinations.ALERTS_MECHANICS_QUEUE, alert);
        System.out.println("[MONITOR] -> Mechanics: " + alert);
    }

    private void sendDriverAlert(String message) {
        DriverAlert alert = new DriverAlert(message);
        jmsTemplate.convertAndSend(Destinations.ALERTS_DRIVER_QUEUE, alert);
        System.out.println("[MONITOR] -> Driver: " + alert);
    }
}
