package algorand.spectrum.websocket.api.requests;

import algorand.spectrum.websocket.api.Rule;
import algorand.spectrum.websocket.api.TxType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@WebAppConfiguration
class SubscriberRequestTest {
    private SubscriberRequest subscriberRequest;
    @Autowired
    private RedisTemplate redisMyTemplate;
    private Rule rule;
    @Mock
    WebSocketSession webSocketSession;

    @BeforeEach
    void setUp() {
        subscriberRequest = new SubscriberRequest();
        rule = new Rule();
        rule.setTrxAccount("trxAccount");
        rule.setTxType(TxType.pay);
        rule.setWebsocketSessionId(new ArrayList<>());
        rule.addWebsocketSessionId("sessionId");
        subscriberRequest.setEvent(Event.subscribe);
        subscriberRequest.setRequestType(RequestType.get_blocks);
    }

    @AfterEach
    void tearDown() {
        redisMyTemplate.delete("get_blocks");
    }

    @Test
    void isValidRequest() {
        assertTrue(subscriberRequest.isValidRequest());
    }
    @Test
    void isValidRequestWithoutEvent(){
        subscriberRequest.setEvent(null);
        assertFalse(subscriberRequest.isValidRequest());
    }
    @Test
    void isValidRequestWithoutRule(){
        subscriberRequest.setData(null);
        assertFalse(subscriberRequest.isValidRequest());
    }
    @Test
    void isValidRequestWithWrongRule(){
        rule.setTrxAccount(null);
        assertFalse(subscriberRequest.isValidRequest());
    }
    @Test
    void processRequestSubscribeGet_blocks(){
        when(webSocketSession.getId()).thenReturn("sessionId");
        subscriberRequest.processRequest(webSocketSession);
        Get_Blocks get_blocks = (Get_Blocks) redisMyTemplate.opsForValue().get("get_blocks");
        assertTrue(get_blocks.getSessionId().contains("sessionId"));
    }

}