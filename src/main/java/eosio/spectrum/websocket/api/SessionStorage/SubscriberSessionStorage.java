package eosio.spectrum.websocket.api.SessionStorage;
import eosio.spectrum.websocket.api.message.RequestType;
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
    private AccountSessionIds accountSessionIdsGetActions = new AccountSessionIds();
    private SessionIdAccounts sessionIdAccountsGetActions = new SessionIdAccounts();
    private AccountSessionIds accountSessionIdsGetTransaction = new AccountSessionIds();
    private SessionIdAccounts sessionIdAccountsGetTransaction = new SessionIdAccounts();
    private AccountSessionIds accountSessionIdsGetBlocks = new AccountSessionIds();
    private SessionIdAccounts sessionIdAccountsGetBlocks = new SessionIdAccounts();
    private AccountSessionIds accountSessionIdsGetTblDeltas = new AccountSessionIds();
    private SessionIdAccounts sessionIdAccountsGetTblDeltas = new SessionIdAccounts();


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
    public void addAccount(String sessionId, String account, RequestType requestType) {
        switch (requestType){
            case get_actions:
                this.sessionIdAccountsGetActions.addAccount(sessionId,account);
                this.accountSessionIdsGetActions.addSession(account,sessionId);
                break;
            case get_blocks:
                this.sessionIdAccountsGetBlocks.addAccount(sessionId,account);
                this.accountSessionIdsGetBlocks.addSession(account,sessionId);
                break;
            case get_transaction:
                this.sessionIdAccountsGetTransaction.addAccount(sessionId,account);
                this.accountSessionIdsGetTransaction.addSession(account,sessionId);
                break;
            case get_table_deltas:
                this.sessionIdAccountsGetTblDeltas.addAccount(sessionId,account);
                this.accountSessionIdsGetTblDeltas.addSession(account,sessionId);
                break;
        }

    }

    public void removeAccount(String sessionId, String account) {
        this.accountSessionIds.removeAccount(account);
        this.sessionIdAccounts.removeAccount(sessionId,account);
    }
    public void removeAccount(String sessionId, String account, RequestType requestType) {
        switch (requestType)
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
