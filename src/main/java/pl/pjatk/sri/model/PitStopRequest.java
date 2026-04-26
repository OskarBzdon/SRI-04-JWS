package pl.pjatk.sri.model;

import java.io.Serializable;

public class PitStopRequest implements Serializable {

    private String reason;
    private int lapNumber;
    private String requestId;

    public PitStopRequest() {}

    public PitStopRequest(String reason, int lapNumber, String requestId) {
        this.reason = reason;
        this.lapNumber = lapNumber;
        this.requestId = requestId;
    }

    public String getReason()    { return reason; }
    public int getLapNumber()    { return lapNumber; }
    public String getRequestId() { return requestId; }

    @Override
    public String toString() {
        return String.format("PitStopRequest{id=%s, lap=%d, reason='%s'}", requestId, lapNumber, reason);
    }
}
