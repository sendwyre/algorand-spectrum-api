package algorand.spectrum.websocket.api.requests;

import algorand.spectrum.websocket.api.Rule;
import algorand.spectrum.websocket.api.TxType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SubscriberRequestTest {
    private SubscriberRequest subscriberRequest;
    private Rule rule;

    @BeforeEach
    void setUp() {
        subscriberRequest = new SubscriberRequest();
        rule = new Rule();
        rule.setTrxAccount("trxAccount");
        rule.setTxType(TxType.pay);
        rule.setWebsocketSessionId(new ArrayList<>());
        rule.addWebsocketSessionId("sessionId");
        subscriberRequest.setEvent(Event.subscribe);
        subscriberRequest.setRequestType(RequestType.get_transaction);
        subscriberRequest.setRule(rule);
    }

    @AfterEach
    void tearDown() {
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

}