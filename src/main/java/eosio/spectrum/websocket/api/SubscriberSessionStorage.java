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
    private AccountSessionIds accountSessionIds = new AccountSessionIds();
    private SessionIdAccounts sessionIdAccounts = new SessionIdAccounts();

    public void saveSession(WebSocketSession session) {
        this.sessions.put(session.getId(), session);
    }

    public void removeSession(String sessionId) {
        sessions.remove(sessionId);
        for (String account:sessionIdAccounts.getAccounts(sessionId)) {
            accountSessionIds.removeSessionId(account, sessionId);
        }
        sessionIdAccounts.removeSession(sessionId);
    }

    public void addAccount(String sessionId, String account) {
        this.sessionIdAccounts.addAccount(sessionId,account);
        this.accountSessionIds.addSession(account,sessionId);
    }

    public void removeAccount(String sessionId, String account) {
        this.accountSessionIds.removeAccount(account);
        this.sessionIdAccounts.removeAccount(sessionId,account);
    }

    public String getSessionId(String account) {
//        return accountSessiondId.get(account);
        return new String();
    }

    public WebSocketSession getSession(String sessionId) {
        return sessions.get(sessionId);
    }

}
