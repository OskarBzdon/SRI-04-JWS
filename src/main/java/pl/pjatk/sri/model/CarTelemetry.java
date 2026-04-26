package pl.pjatk.sri.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class CarTelemetry implements Serializable {

    private double engineTemp;
    private double oilPressure;
    private double tirePressureFL;
    private double tirePressureFR;
    private double tirePressureRL;
    private double tirePressureRR;
    private LocalDateTime timestamp;

    public CarTelemetry() {}

    public CarTelemetry(double engineTemp, double oilPressure,
                        double tirePressureFL, double tirePressureFR,
                        double tirePressureRL, double tirePressureRR) {
        this.engineTemp = engineTemp;
        this.oilPressure = oilPressure;
        this.tirePressureFL = tirePressureFL;
        this.tirePressureFR = tirePressureFR;
        this.tirePressureRL = tirePressureRL;
        this.tirePressureRR = tirePressureRR;
        this.timestamp = LocalDateTime.now();
    }

    public double getEngineTemp()       { return engineTemp; }
    public double getOilPressure()      { return oilPressure; }
    public double getTirePressureFL()   { return tirePressureFL; }
    public double getTirePressureFR()   { return tirePressureFR; }
    public double getTirePressureRL()   { return tirePressureRL; }
    public double getTirePressureRR()   { return tirePressureRR; }
    public LocalDateTime getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return String.format(
            "CarTelemetry{engineTemp=%.1f, oilPressure=%.2f, tires=[FL=%.2f FR=%.2f RL=%.2f RR=%.2f], time=%s}",
            engineTemp, oilPressure, tirePressureFL, tirePressureFR, tirePressureRL, tirePressureRR, timestamp);
    }
}
