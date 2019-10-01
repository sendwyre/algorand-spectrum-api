package eosio.spectrum.websocket.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eosio.spectrum.websocket.api.SessionStorage.SubscriberSessionStorage;
import eosio.spectrum.websocket.api.message.FilteredAction;
import eosio.spectrum.websocket.api.message.RequestType;
import eosio.spectrum.websocket.api.message.ResponseGetActions;
import eosio.spectrum.websocket.api.message.ServiceMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;


@Component
public class MessageListenerService {

    private static final transient Logger logger = LoggerFactory.getLogger(MessageListenerService.class);

    private FilterRulesStorage filterRulesStorage;

    @Autowired
    private void setFilterRulesStorage(FilterRulesStorage filterRulesStorage){
        this.filterRulesStorage = filterRulesStorage;
    }

    public void handleMessage(String message) {
        logger.debug(message);
                Gson gson = new GsonBuilder().serializeNulls().create();
        ServiceMessage serviceMessage = gson.fromJson(message, ServiceMessage.class);

        switch (serviceMessage.getEvent()) {
            case subscribe:
                switch (serviceMessage.getRequestType()){
                    case get_actions:
                        filterRulesStorage.addRule(serviceMessage.getData().getAccount(),serviceMessage.getData().getActions(),RequestType.get_actions);
                        break;
                    case get_transaction:
                        break;
                    case get_table_deltas:
                        break;
                    case get_blocks:
                        break;
                }
                break;
            case unsubscribe:
                switch (serviceMessage.getRequestType()){
                    case get_actions:
                        filterRulesStorage.removeRule(serviceMessage.getData().getAccount(), serviceMessage.getData().getActions(), RequestType.get_actions);
                        break;
                }
                break;
        }
    }
}
