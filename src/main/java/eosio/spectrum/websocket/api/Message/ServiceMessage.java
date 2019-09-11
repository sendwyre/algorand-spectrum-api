package eosio.spectrum.websocket.api.Message;

import com.google.gson.annotations.SerializedName;
import eosio.spectrum.websocket.api.Message.Event;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServiceMessage {

    @SerializedName("type")
    private RequestType type;

    @SerializedName("event")
    private Event event;

    @SerializedName("data")
    private Data data;


    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public RequestType getRequestType() {
        return type;
    }

    public void setRequestType(RequestType type) {
        this.type = type;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {

        this.data = data;
    }


}
