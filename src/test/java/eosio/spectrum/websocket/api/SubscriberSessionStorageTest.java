package eosio.spectrum.websocket.api;

import org.junit.Test;

import java.util.HashSet;
import java.util.UUID;

import static org.junit.Assert.*;

public class SubscriberSessionStorageTest {
    public static SubscriberSessionStorage subscriberSessionStorage = new SubscriberSessionStorage();

    @Test
    public void getAccounts() {

    }

    @Test
    public void removeSessionIdAccounts() {

        String userFirstAccount1 = "testaccount1";
        String userFirstAccount2 = "testaccount2";
        HashSet<String> accountsUserFirst = new HashSet<>();
        accountsUserFirst.add(userFirstAccount1);
        accountsUserFirst.add(userFirstAccount2);
        UUID uiid = UUID.randomUUID();
        String userFirstSessionId = uiid.toString();
        subscriberSessionStorage.saveSessionIdAccounts(userFirstSessionId,userFirstAccount1);
        subscriberSessionStorage.saveSessionIdAccounts(userFirstSessionId,userFirstAccount2);

        String userSecondAccount1 = "secondaccount1";
        String userSecondAccount2 = "secondaccount2";

        HashSet<String> accountsUserSecond = new HashSet<>();
        accountsUserSecond.add(userSecondAccount1);
        accountsUserSecond.add(userSecondAccount2);
        uiid = UUID.randomUUID();
        String userSecondSessionId = uiid.toString();

        subscriberSessionStorage.saveSessionIdAccounts(userSecondSessionId,userSecondAccount1);
        subscriberSessionStorage.saveSessionIdAccounts(userSecondSessionId,userSecondAccount2);

        subscriberSessionStorage.removeSessionIdAccounts(userFirstSessionId);


        assertNull(subscriberSessionStorage.getSessionId(userFirstAccount1));
        assertNull(subscriberSessionStorage.getSessionId(userFirstAccount2));
        assertNull(subscriberSessionStorage.getAccounts(userFirstSessionId));
        assertEquals(accountsUserSecond,subscriberSessionStorage.getAccounts(userSecondSessionId));

    }

    @Test
    public void getSessionId() {
    }

    @Test
    public void getSession() {
    }
}