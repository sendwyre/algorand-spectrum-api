package eosio.spectrum.websocket.api;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionIdAccounts {

    private Map<String, HashSet<String>> records;

    public SessionIdAccounts(){
        records = new ConcurrentHashMap<>();
    }

    public  void  addAccount(String sessionId,String account){
        if (records.containsKey(sessionId)){
            HashSet accounts = records.get(sessionId);
            accounts.add(account);
            records.replace(sessionId, accounts);
        }else {
            HashSet accounts = new HashSet();
            accounts.add(account);
            records.put(sessionId, accounts);
        }
    }

    public  void removeAccount(String sessionID, String account){
        HashSet accounts = records.get(sessionID);
        accounts.remove(account);
        records.replace(sessionID,accounts);
    }

    public  void removeSession(String session){
        records.remove(session);
    }

    public HashSet getAccounts(String sessionId){
        return this.records.get(sessionId);
    }

    public boolean containsSessionId(String sessionId){
        return this.records.containsKey(sessionId);

    }
}
