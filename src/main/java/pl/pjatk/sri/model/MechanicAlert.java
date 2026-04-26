package pl.pjatk.sri.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class MechanicAlert implements Serializable {

    private String problem;
    private String severity;
    private LocalDateTime timestamp;

    public MechanicAlert() {}

    public MechanicAlert(String problem, String severity) {
        this.problem = problem;
        this.severity = severity;
        this.timestamp = LocalDateTime.now();
    }

    public String getProblem()          { return problem; }
    public String getSeverity()         { return severity; }
    public LocalDateTime getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return String.format("MechanicAlert{severity=%s, problem='%s', time=%s}", severity, problem, timestamp);
    }
}
