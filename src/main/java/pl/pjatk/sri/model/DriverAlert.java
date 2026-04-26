package pl.pjatk.sri.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DriverAlert implements Serializable {

    private String message;
    private LocalDateTime timestamp;

    public DriverAlert() {}

    public DriverAlert(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public String getMessage()          { return message; }
    public LocalDateTime getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return String.format("DriverAlert{message='%s', time=%s}", message, timestamp);
    }
}
