package eosio.spectrum.websocket.api;

import java.util.List;

public class SubscribeRequest {


    private String apikey;

    private RequestType type;

    private Event event;

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
        return "SubscribeRequest{" +
                "apikey='" + apikey + '\'' +
                ", requestType=" + type +
                ", event=" + event +
                ", data=" + data.toString() +
                '}';
    }
}

