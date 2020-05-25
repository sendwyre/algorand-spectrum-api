package algorand.spectrum.websocket.api.ws;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;

@Component
public class WebsocketSessionStorage {
    Map sessions;

    public WebsocketSessionStorage(){
        sessions = new HashMap();
    }

    public void addSession(WebSocketSession session){
        sessions.put(session.getId(),session);
    }
    public void delSession(String session){
        sessions.remove(session);
    }

}
