package eos.websocket.api;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;


@Component
@EnableWebSocket
public class SocketHandler extends BinaryWebSocketHandler implements WebSocketHandler{
//    List sessions = new CopyOnWriteArrayList<>();
    ByteBuffer buffer;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {

    }

    @Override
    public void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws UnsupportedEncodingException {

        String mess = new String(message.getPayload().array(),"UTF-8");
        System.out.println(mess);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        System.out.println(status);

    }



}
