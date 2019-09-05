package eos.websocket.api;


import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.List;


@Service
public class SubscriberSessionStorage {
    private WebSocketSession session;
    private String sessionId;
    private HashMap<String,WebSocketSession> sessions = new HashMap<>();
    private HashMap<String,String> accountsSessionID = new HashMap<>();


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

    public String getIdByAccountName(String accountName){
        return accountsSessionID.get(accountName);
    }
}
