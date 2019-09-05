package eos.websocket.api;

import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class SubscribeMessage {
    private Events events;
    private String account;
    private List<String> actions;

    public Events getEvents() {
        return events;
    }

    public void setEvents(Events events) {
        this.events = events;
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
        return "SubscribeMessage{" +
                "events=" + events +
                ", account='" + account + '\'' +
                ", actions=" + actions +
                '}';
    }
}
