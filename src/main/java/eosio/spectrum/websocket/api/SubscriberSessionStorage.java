package eosio.spectrum.websocket.api;


import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service
@SuppressWarnings("unchecked")
public class SubscriberSessionStorage {


    private Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private Map<String, HashSet<String>> sessionIdAccounts = new ConcurrentHashMap<>();
    private Map<String, String> accountSessiondId = new ConcurrentHashMap<>();

    public void saveGetActionsSession(String account, String sessionId){

    }


    public void saveSession(WebSocketSession session) {
        this.sessions.put(session.getId(), session);
    }

    public void removeSession(String sessionId) {
        sessions.remove(sessionId);

    }

    public HashSet<String> getAccounts(String sessionId) {
        return sessionIdAccounts.get(sessionId);
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
