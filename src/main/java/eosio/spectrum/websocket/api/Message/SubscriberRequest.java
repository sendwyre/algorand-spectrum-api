package eosio.spectrum.websocket.api.Message;

import com.google.gson.annotations.SerializedName;
import eosio.spectrum.websocket.api.Message.Data;
import eosio.spectrum.websocket.api.Message.Event;
import eosio.spectrum.websocket.api.Message.RequestType;

import javax.validation.constraints.NotNull;

public class SubscriberRequest {
    @SerializedName("api-key")
    private String apikey;

    @SerializedName("type")
    private RequestType type;

    @SerializedName("event")
    private Event event;

    @SerializedName("data")
    private Data data;

    public String getApiKey() {
        return apikey;
    }

    public void setApiKey(String apiKey) {
        this.apikey = apiKey;
    }

    public RequestType getRequestType() {
        return type;
    }

    public void setRequestType(RequestType type) {
        this.type = type;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SubscriberRequest{" +
                "apikey='" + apikey + '\'' +
                ", requestType=" + type +
                ", event=" + event +
                ", data=" + data.toString() +
                '}';
    }
}

