package eosio.spectrum.websocket.api;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.UUID;

import static org.junit.Assert.*;

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
        userFirstAccount1 = "testaccount1";
        userFirstAccount2 = "testaccount2";
        HashSet<String> accountsUserFirst = new HashSet<>();
        accountsUserFirst.add(userFirstAccount1);
        accountsUserFirst.add(userFirstAccount2);
        uiid = UUID.randomUUID();
        userFirstSessionId = uiid.toString();


        userSecondAccount1 = "secondaccount1";
        userSecondAccount2 = "secondaccount2";

        accountsUserSecond = new HashSet<>();
        accountsUserSecond.add(userSecondAccount1);
        accountsUserSecond.add(userSecondAccount2);
        uiid = UUID.randomUUID();
        userSecondSessionId = uiid.toString();

        subscriberSessionStorage.addAccount(userSecondSessionId,userSecondAccount1);
        subscriberSessionStorage.addAccount(userSecondSessionId,userSecondAccount2);

    }

    @Test
    public void getAccounts() {

    }

    @Test
    public void removeSessionIdAccounts() {
        subscriberSessionStorage.removeSession(userFirstSessionId);

        assertNull(subscriberSessionStorage.getSessionId(userFirstAccount1));
        assertNull(subscriberSessionStorage.getSessionId(userFirstAccount2));
//        assertNull(subscriberSessionStorage.getAccounts(userFirstSessionId));
//        assertEquals(accountsUserSecond,subscriberSessionStorage.getAccounts(userSecondSessionId));
    }

    @Test
    public void getSessionId() {
        assertEquals(userFirstSessionId,subscriberSessionStorage.getSessionId(userFirstAccount1));
        assertEquals(userFirstSessionId,subscriberSessionStorage.getSessionId(userFirstAccount2));

        assertEquals(userSecondSessionId,subscriberSessionStorage.getSessionId(userSecondAccount1));
        assertEquals(userSecondSessionId,subscriberSessionStorage.getSessionId(userSecondAccount2));

    }

    @Test
    public void getSession() {
    }
}