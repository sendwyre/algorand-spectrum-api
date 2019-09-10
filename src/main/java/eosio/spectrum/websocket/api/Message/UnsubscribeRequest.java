package eosio.spectrum.websocket.api.Message;

import com.google.gson.annotations.SerializedName;

public class UnsubscribeRequest {
    @SerializedName("session-id")
    private String sessionid;
    private Event event;

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "UnsubscribeRequest {" +
                "sessionid='" + sessionid + '\'' +
                ", event=" + event +
                '}';
    }
}
