package eos.websocket.api;


import org.apache.kafka.common.protocol.types.Field;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class SubscriberSessionStorage {
    private WebSocketSession session;
    private String sessionId;
    private Map<String,WebSocketSession> sessions = new ConcurrentHashMap<>();
    private Map<String,String> accountsSessionID = new ConcurrentHashMap<>();
    private Map<String,List<String>> sessionIDaccounts = new ConcurrentHashMap<>();


    public void setSession(WebSocketSession session) {
        this.sessions.put(session.getId(),session);
    }

    public void setAccountsSessionID(String account, String sessionId){
        this.accountsSessionID.put(account,sessionId);
    }

    public void setAccountsSessionID(List<String> accountsList, String sessionId){
        for (String account:accountsList ) {
            this.accountsSessionID.put(account,sessionId);
        }
    }

    public WebSocketSession getSessionByID(String sessionId){
        return sessions.get(sessionId);
    }


    public void removeActions(String sessionId){
        while (accountsSessionID.containsValue(sessionId)){
            this.accountsSessionID.remove(
                    getKeyFromValue(accountsSessionID, sessionId)
            );
        }

    }

    private Object getKeyFromValue(Map hm, Object value) {
        for (Object o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }

    public String getIdByAccountName(String accountName){
        return accountsSessionID.get(accountName);
    }
}
