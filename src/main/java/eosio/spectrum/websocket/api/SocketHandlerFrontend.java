package eosio.spectrum.websocket.api;

import com.google.gson.*;
import eosio.spectrum.websocket.api.Message.*;
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

import java.io.IOException;
import java.util.ArrayList;

@Component
@EnableWebSocket
public class SocketHandlerFrontend extends TextWebSocketHandler implements WebSocketHandler {

    private static final transient Logger logger = LoggerFactory.getLogger(SocketHandlerFrontend.class);

    private SubscriberSessionStorage subscriberSessionStorage;

    private ServiceMessage serviceMessage;

    private RedisMessagePublisherService redisMessagePublisherService;

    private SubscriberRequest subscriberRequest;
//    private UnsubscribeRequest unsubscribeRequest;

    @Autowired
    public void setRedisMessagePublisherService(RedisMessagePublisherService redisMessagePublisherService) {
        this.redisMessagePublisherService = redisMessagePublisherService;
    }


    @Autowired
    public void setServiceMessage(ServiceMessage serviceMessage) {
        this.serviceMessage = serviceMessage;
    }


    @Autowired
    public void setSubscriberSessionStorage(SubscriberSessionStorage subscriberSessionStorage) {
        this.subscriberSessionStorage = subscriberSessionStorage;
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        subscriberSessionStorage.saveSession(session);
        logger.debug("Chronicle connected from: "+session.getRemoteAddress());
    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            subscriberRequest = new Gson().fromJson(message.getPayload(), SubscriberRequest.class);

            if (subscriberRequest.getRequestType() == null | subscriberRequest.getEvent() == null)
            {
                String infoMessage = "Unable to proceed request, fill fields according to documentation";
                session.sendMessage(new TextMessage(infoMessage));
                logger.warn("Request from "+session.getRemoteAddress()+" has unknown format");
                logger.warn("message body : "+message.getPayload());
                session.close();

            }else
                {
                    switch (subscriberRequest.getEvent())
                    {
                        case subscribe:
                            switch (subscriberRequest.getRequestType())
                            {
                                case get_actions:

                                    subscriberSessionStorage.saveSessionIdAccounts(session.getId(), subscriberRequest.getData().getAccount());

                                    serviceMessage.setEvent(subscriberRequest.getEvent());
                                    serviceMessage.setRequestType(subscriberRequest.getRequestType());
                                    serviceMessage.setData(subscriberRequest.getData());

                                    redisMessagePublisherService.publish(new Gson().toJson(serviceMessage));
                                    break;
                                case get_transaction:
                                    break;
                                case get_table_deltas:
                                    break;
                                case get_blocks:
                                    break;
                                case ping:
                                    break;
                            }
                            break;
                        case unsubscribe:
                            subscriberRequest.getRequestType();
                    break;
                default:
                    String infoMessage = "Value of event field is unknown";
                    session.sendMessage(new TextMessage(infoMessage));
                    logger.warn("Request from "+session.getRemoteAddress()+" has unknown format");
                    session.close();
                  }
            }
        }
        catch (JsonIOException|JsonSyntaxException e){
            String infoMessage = "Unknown type of request";
            session.sendMessage(new TextMessage(infoMessage));
            logger.warn("Request from "+session.getRemoteAddress()+" has unknown format");
            logger.warn(e.toString());
            session.close();
        }
        }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        String sessionId = session.getId();
//        unsubscribe(session);


    }

    public void handleMessage(String message) throws IOException {

        JSONObject jsonMessage = new JSONObject(message);

        String accountid = jsonMessage.getString("accountID");
        String sessionid = subscriberSessionStorage.getSessionId(accountid);
        WebSocketSession session = subscriberSessionStorage.getSession(sessionid);
        synchronized (session) {
            if (session != null && session.isOpen()) session.
                    sendMessage(new TextMessage(jsonMessage.getJSONObject("action").toString()));
        }
        logger.info("Message " + message);
    }

    private void unsubscribe(WebSocketSession session) {

        String sessionID = session.getId();

        for (String account : subscriberSessionStorage.getAccounts(sessionID)) {
            serviceMessage.setEvent(Event.unsubscribe);
//            serviceMessage.setAccount(account);
            redisMessagePublisherService.publish(new Gson().toJson(serviceMessage));
        }

        subscriberSessionStorage.removeSessionIdAccounts(sessionID);
        subscriberSessionStorage.removeSession(sessionID);

    }
}
