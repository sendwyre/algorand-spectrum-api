package eosio.spectrum.websocket.api;

import eosio.spectrum.websocket.api.SessionStorage.SubscriberSessionStorage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class SubscriberSessionStorageTest {
    public SubscriberSessionStorage subscriberSessionStorage;
    public String userFirstSessionId;
    public String userFirstAccount1;
    public String userFirstAccount2;
    public UUID uiid;

    String userSecondSessionId;
    String userSecondAccount1;
    String userSecondAccount2;

    HashSet<String> accountsUserSecond;

    @Before
    public void init() throws Exception{
        subscriberSessionStorage = new SubscriberSessionStorage();

        userFirstAccount1 = "userFirstAccount1";
        userFirstAccount2 = "userFirstAccount2";

        uiid = UUID.randomUUID();
        userFirstSessionId = uiid.toString();
        uiid = UUID.randomUUID();
        userSecondSessionId = uiid.toString();

        userSecondAccount1 = "userSecondAccount1";
        userSecondAccount2 = "userSecondAccount2";


        subscriberSessionStorage.addAccount(userFirstSessionId,userFirstAccount1);
        subscriberSessionStorage.addAccount(userFirstSessionId,userFirstAccount2);
        subscriberSessionStorage.addAccount(userSecondSessionId,userSecondAccount1);
        subscriberSessionStorage.addAccount(userSecondSessionId,userSecondAccount2);

    }

    @Test
    public void getSessionsID() {
        assertTrue(subscriberSessionStorage.getSessionsId(userFirstAccount1).contains(userFirstSessionId));
        assertTrue(subscriberSessionStorage.getSessionsId(userFirstAccount2).contains(userFirstSessionId));
        assertTrue(subscriberSessionStorage.getSessionsId(userSecondAccount1).contains(userSecondSessionId));
        assertTrue(subscriberSessionStorage.getSessionsId(userSecondAccount2).contains(userSecondSessionId));
    }


    @Test
    public void removeAccount() {
        subscriberSessionStorage.removeAccount(userFirstSessionId, userFirstAccount1);
        subscriberSessionStorage.removeAccount(userSecondSessionId, userSecondAccount1);
        assertTrue(subscriberSessionStorage.getSessionsId(userFirstAccount2).contains(userFirstSessionId));
        assertTrue(subscriberSessionStorage.getSessionsId(userSecondAccount2).contains(userSecondSessionId));

    }
//    @Test
//    public void getSession() {
//    }
}