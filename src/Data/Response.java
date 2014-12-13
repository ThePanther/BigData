package Data;


public class Response {

    private boolean state;
    private String reason;

    public Response(boolean state, String reason) {
        this.state = state;
        this.reason = reason;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
