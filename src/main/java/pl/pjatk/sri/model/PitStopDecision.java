package pl.pjatk.sri.model;

import java.io.Serializable;

public class PitStopDecision implements Serializable {

    private boolean accepted;
    private String justification;
    private String requestId;

    public PitStopDecision() {}

    public PitStopDecision(boolean accepted, String justification, String requestId) {
        this.accepted = accepted;
        this.justification = justification;
        this.requestId = requestId;
    }

    public boolean isAccepted()       { return accepted; }
    public String getJustification()  { return justification; }
    public String getRequestId()      { return requestId; }

    @Override
    public String toString() {
        return String.format("PitStopDecision{id=%s, accepted=%b, justification='%s'}", requestId, accepted, justification);
    }
}
