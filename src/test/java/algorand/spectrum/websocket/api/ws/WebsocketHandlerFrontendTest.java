package algorand.spectrum.websocket.api.ws;

import org.apache.logging.log4j.message.Message;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
class WebsocketHandlerFrontendTest {
    @Mock WebSocketSession webSocketSession;
    WebsocketHandlerFrontend websocketHandlerFrontend;
    TextMessage message;
    @BeforeEach
    void setUp(){
        websocketHandlerFrontend = new WebsocketHandlerFrontend();
        message = new TextMessage("message");
    }
    @AfterEach
    void tearDown() {
    }

    @Test
    void afterConnectionEstablished() throws Exception {
        websocketHandlerFrontend.afterConnectionEstablished(webSocketSession);
    }

    @Test
    void handleTextMessage() throws Exception {
        websocketHandlerFrontend.handleTextMessage(webSocketSession,message);
    }

    @Test
    void afterConnectionClosed() {
    }

    @Test
    void addRule() {
    }

    @Test
    void delRule() {
    }
}