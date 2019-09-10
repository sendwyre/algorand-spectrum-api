package eosio.spectrum.websocket.api.Message;

import eosio.spectrum.websocket.api.Message.Event;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServiceMessage {

    private Event event;
    private String account;
    private RequestType requestType;

    private List<String> actions;

    private Data data;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public List<String> getActions() {
        return actions;
    }

    public void setActions(List<String> actions) {
        this.actions = actions;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{" +
                "event=" + event +
                ", account='" + account + '\'' +
                ", actions=" + actions +
                '}';
    }
}
