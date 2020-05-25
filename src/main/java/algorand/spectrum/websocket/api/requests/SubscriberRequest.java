package algorand.spectrum.websocket.api.requests;

import algorand.spectrum.websocket.api.ApplicationContextProvider;
import algorand.spectrum.websocket.api.Rule;
import algorand.spectrum.websocket.api.configuration.RedisConfiguration;
import algorand.spectrum.websocket.api.ws.WebsocketSessionStorage;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.socket.WebSocketSession;

public class SubscriberRequest {
    @SerializedName("api-key")
    private String apikey;

    @SerializedName("type")
    private RequestType type;

    @SerializedName("event")
    private Event event;

    @SerializedName("data")
    private Data data;

    private Rule rule;

    private WebsocketSessionStorage websocketSessionStorage = (WebsocketSessionStorage) ApplicationContextProvider.
            getApplicationContext().getBean("websocketSessionStorage");
    private RedisTemplate redisMyTemplate = (RedisTemplate) ApplicationContextProvider.
            getApplicationContext().
            getBean("redisMyTemplate");

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

    public boolean isValidRequest(){
        if (event == null | type == null){
            return false;
        }
        if (type.equals(RequestType.get_transactions) & data == null){
            return false;
        }
        return true ;
    }
    public void processRequest(WebSocketSession session){
        switch (event){
            case subscribe:
                switch (type){
                    case get_blocks:
                        Get_Blocks get_blocks = (Get_Blocks) redisMyTemplate.opsForValue().get("get_blocks");
                        if (get_blocks == null){
                            get_blocks = new Get_Blocks();
                        }
                        get_blocks.addSession(session.getId());
                        redisMyTemplate.opsForValue().set("get_blocks", get_blocks);
                        break;
                    case get_transactions:
                        break;
                }
                break;
            case unsubscribe:
                switch (type){
                    case get_blocks:
                        Get_Blocks get_blocks = (Get_Blocks) redisMyTemplate.opsForValue().get("get_blocks");
                        get_blocks.delSession(session.getId());
                        break;
                    case get_transactions:
                        break;
                }
                break;
        }

    }
}


