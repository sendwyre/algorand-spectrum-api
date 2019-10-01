package eosio.spectrum.websocket.api.SessionStorage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
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
        sessions.remove(sessionId);
    }

    public void addAccount(String sessionId, String account) {
        this.sessionIdAccounts.addAccount(sessionId,account);
        this.accountSessionIds.addSession(account,sessionId);
    }


    public void removeAccount(String sessionId, String account) {
        this.accountSessionIds.removeAccount(account);
        this.sessionIdAccounts.removeAccount(sessionId,account);
    }

    public HashSet<String> getAccounts(String sessionId){
        return this.sessionIdAccounts.getAccounts(sessionId);
    }

    public HashSet<String> getSessionsId(String account) {
        return this.accountSessionIds.getSessionIds(account);
    }

    public WebSocketSession getSession(String sessionId) {
        return sessions.get(sessionId);
    }

}
