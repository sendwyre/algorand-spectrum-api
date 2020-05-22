package algorand.spectrum.websocket.api.ws;

import algorand.spectrum.websocket.api.Rule;
import algorand.spectrum.websocket.api.requests.SubscriberRequest;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@EnableWebSocket
@Component
public class WebsocketHandlerFrontend extends TextWebSocketHandler implements WebSocketHandler {
    private static final transient Logger logger = LoggerFactory.getLogger(WebsocketHandlerFrontend.class);

    private SubscriberRequest subscriberRequest;
    private RedisTemplate redisMyTemplate;
    @Autowired
    public void setRedisMyTemplate(RedisTemplate redisMyTemplate){
        this.redisMyTemplate = redisMyTemplate;
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info(session.getUri().toString());
        logger.debug("Connected from: "+session.getRemoteAddress());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            subscriberRequest = new Gson().fromJson(message.getPayload(), SubscriberRequest.class);
            if (subscriberRequest.isValidRequest() != true) {
                String infoMessage = "Unable to proceed request, fill fields according to documentation";
                session.sendMessage(new TextMessage(infoMessage));
                logger.warn("Request from " + session.getRemoteAddress() + " has unknown format");
                logger.warn("message body : " + message.getPayload());
                session.close();
            }
        } catch (Exception e) {
            logger.info(e.toString());
        }
    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

    }

    public void addRule(Rule rule){
        redisMyTemplate.opsForValue().set(rule.getTrxAccount(),rule);
    }
    public void delRule(String trxAccount){
        redisMyTemplate.delete(trxAccount);
    }

}
