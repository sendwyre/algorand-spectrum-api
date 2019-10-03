package eosio.spectrum.websocket.api.RedisTopicListeners;

import eosio.spectrum.websocket.api.SessionStorage.SubscriberSessionStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class MessageListenerBlocks {

    private static final transient Logger logger = LoggerFactory.getLogger(MessageListenerBlocks.class);

    private SubscriberSessionStorage subscriberSessionStorage;

    @Autowired
    private void setSubscriberSessionStorage(SubscriberSessionStorage subscriberSessionStorage){
        this.subscriberSessionStorage = subscriberSessionStorage;
    }

    public void handleMessage(String message) {
        logger.info(message);
    }
}
