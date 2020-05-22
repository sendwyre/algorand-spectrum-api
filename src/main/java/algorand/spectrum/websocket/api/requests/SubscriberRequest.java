package algorand.spectrum.websocket.api.requests;

import algorand.spectrum.websocket.api.Rule;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class SubscriberRequest {
    @SerializedName("api-key")
    private String apikey;

    @SerializedName("type")
    private RequestType type;

    @SerializedName("event")
    private Event event;

    @SerializedName("data")
    private Rule rule;

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

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public boolean isValidRequest(){

        return event != null | type !=null | rule.getTrxAccount() !=null;
    }

    @Override
    public String toString() {
        return "SubscriberRequest{" +
                "apikey='" + apikey + '\'' +
                ", type=" + type +
                ", event=" + event +
                ", rule=" + rule +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SubscriberRequest that = (SubscriberRequest) o;

        return new EqualsBuilder()
                .append(apikey, that.apikey)
                .append(type, that.type)
                .append(event, that.event)
                .append(rule, that.rule)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(apikey)
                .append(type)
                .append(event)
                .append(rule)
                .toHashCode();
    }
}


