package eosio.spectrum.websocket.api;


import eosio.spectrum.websocket.api.message.RequestType;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import javax.validation.constraints.Null;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service
@SuppressWarnings("unchecked")
public class SubscriberSessionStorage {


    private Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private Map<String, HashSet<String>> sessionIdAccounts = new ConcurrentHashMap<>();
    private Map<String, HashSet<String>> accountSessiondId = new ConcurrentHashMap<>();

    public void saveWebsocketSession(String account, WebSocketSession webSocketSession, RequestType requestType){
        String sesionId = webSocketSession.getId();
        if (sessions.get(sesionId) == null){
            sessions.put(sesionId,webSocketSession);
        }
        switch (requestType){
            case get_table_deltas:
                break;
            case get_transaction:
                break;
            case get_blocks:
                break;
            case get_actions:
                this.sessions.put(webSocketSession.getId(),webSocketSession);
                this.accountSessiondId.put(account, sesionId);

                this.sessionIdAccounts.put(sesionId,new HashSet<>().add(account));
//                this.sessionIdAccounts(webSocketSession.getId(), )
                break;
        }


    }

    public WebSocketSession getWebsocketSession(String account, RequestType requestType){
        switch (requestType){
            case get_table_deltas:
                break;
            case get_transaction:
                break;
            case get_blocks:
                break;
            case get_actions:
                break;
        }
        return sessions.get("");
    }



    public void saveSession(WebSocketSession session) {
        this.sessions.put(session.getId(), session);
    }

    public void removeSession(String sessionId) {
        sessions.remove(sessionId);

    }



    public void saveSessionIdAccounts(String sessionId, String account) {
//        check whether the provided account already is saved

        if (sessionIdAccounts.get(sessionId) == null) {
            HashSet<String> accounts = new HashSet();
            accounts.add(account);
            sessionIdAccounts.put(sessionId, accounts);
            accountSessiondId.put(account, sessionId);

        } else {
            HashSet<String> accounts = sessionIdAccounts.get(sessionId);
            accounts.add(account);
            sessionIdAccounts.replace(sessionId, accounts);
            accountSessiondId.put(account, sessionId);
        }
    }

    public void removeSessionIdAccounts(String sessionId) {
        for (String account:sessionIdAccounts.get(sessionId)) {
            accountSessiondId.remove(account);
        }
        sessionIdAccounts.remove(sessionId);
    }

    public void removeAccount(String account){
        sessionIdAccounts.remove(accountSessiondId.get(account));
        accountSessiondId.remove(account);
    }

    public String getSessionId(String account) {
        return accountSessiondId.get(account);
    }

    public WebSocketSession getSession(String sessionId) {
        return sessions.get(sessionId);
    }

}
