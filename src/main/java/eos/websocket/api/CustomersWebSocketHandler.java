package eos.websocket.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;

@Component
@EnableWebSocket
public class CustomersWebSocketHandler extends TextWebSocketHandler implements WebSocketHandler{
    private static final transient Logger logger = LoggerFactory.getLogger(CustomersWebSocketHandler.class);

    private SubscriberSessionStorage subscriberSessionStorage;

    private SubscribeMessage subscribeMessage;

    private RedisMessagePublisher redisMessagePublisher;

    @Autowired
    public void setRedisMessagePublisher(RedisMessagePublisher redisMessagePublisher) {
        this.redisMessagePublisher = redisMessagePublisher;
    }

    @Autowired
    public void setSubscribeMessage(SubscribeMessage subscribeMessage){
        this.subscribeMessage = subscribeMessage;
    }


    @Autowired
    public void setSubscriberSessionStorage(SubscriberSessionStorage subscriberSessionStorage){
        this.subscriberSessionStorage = subscriberSessionStorage;
         }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        subscriberSessionStorage.setSession(session);
        logger.info(session.getId());


    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        SubscribeRequest subscribeRequest = new Gson().fromJson(message.getPayload(),SubscribeRequest.class);
        subscriberSessionStorage.setAccountsSessionID(subscribeRequest.getAccount(), session.getId());

        subscribeMessage.setEvents(Events.subscribe);
        subscribeMessage.setAccount(subscribeRequest.getAccount());
        subscribeRequest.setActions(subscribeRequest.getActions());

        redisMessagePublisher.publish(subscribeMessage.toString());



        logger.info(subscribeRequest.toString());

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        getSessionStorage().remove(session.getId());
    }

    public void  handleMessage(String message){
        logger.info(message);
     }
}
