package eos.websocket.api;

import com.google.gson.Gson;
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

@Component
@EnableWebSocket
public class SocketHandlerFrontend extends TextWebSocketHandler implements WebSocketHandler{
    private static final transient Logger logger = LoggerFactory.getLogger(SocketHandlerFrontend.class);

    private SubscriberSessionStorage subscriberSessionStorage;

    private SubscribeMessage subscribeMessage;

    private RedisMessagePublisherService redisMessagePublisherService;

    @Autowired
    public void setRedisMessagePublisherService(RedisMessagePublisherService redisMessagePublisherService){
        this.redisMessagePublisherService = redisMessagePublisherService;
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

        subscribeMessage.setEvent(Events.subscribe);
        subscribeMessage.setAccount(subscribeRequest.getAccount());
        subscribeMessage.setActions(subscribeRequest.getActions());

        redisMessagePublisherService.publish(new Gson().toJson(subscribeMessage));



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
