package eosio.spectrum.websocket.api.Message;

import eosio.spectrum.websocket.api.Message.Event;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServiceMessage {

    private Event event;
    private String account;
    private List<String> actions;

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

    @Override
    public String toString() {
        return "{" +
                "event=" + event +
                ", account='" + account + '\'' +
                ", actions=" + actions +
                '}';
    }
}
