package eos.websocket.api;

import com.google.gson.Gson;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Component
@EnableWebSocket
public class SocketHandlerFrontend extends TextWebSocketHandler implements WebSocketHandler{
    private static final transient Logger logger = LoggerFactory.getLogger(SocketHandlerFrontend.class);

    private SubscriberSessionStorage subscriberSessionStorage;

    private ServiceMessage serviceMessage;

    private RedisMessagePublisherService redisMessagePublisherService;

    @Autowired
    public void setRedisMessagePublisherService(RedisMessagePublisherService redisMessagePublisherService){
        this.redisMessagePublisherService = redisMessagePublisherService;
    }


    @Autowired
    public void setServiceMessage(ServiceMessage serviceMessage){
        this.serviceMessage = serviceMessage;
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

        serviceMessage.setEvent(Events.subscribe);
        serviceMessage.setAccount(subscribeRequest.getAccount());
        serviceMessage.setActions(subscribeRequest.getActions());

        redisMessagePublisherService.publish(new Gson().toJson(serviceMessage));



        logger.info(subscribeRequest.toString());

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

    }

    public void  handleMessage(String message) throws IOException {

        JSONObject jsonMessage = new JSONObject(message);

        String accountid = jsonMessage.getString("accountID");
        String sessionid = subscriberSessionStorage.getIdByAccountName(accountid);
        WebSocketSession session = subscriberSessionStorage.getSessionByID(sessionid);
        if (session !=null ) session.sendMessage( new TextMessage(jsonMessage.getJSONObject("action").toString()));
        logger.info("Message "+message);
     }
}
