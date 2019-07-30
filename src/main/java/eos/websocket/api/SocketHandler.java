package eos.websocket.api;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;


@Component
@EnableWebSocket
public class SocketHandler extends BinaryWebSocketHandler implements WebSocketHandler{
//    List sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println(session.getAcceptedProtocol());
        System.out.println(session.getTextMessageSizeLimit());
    }

    @Override
    public void handleBinaryMessage(WebSocketSession session, BinaryMessage message){

        System.out.println(message.getPayload().toString());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        System.out.println(status);

    }



}
