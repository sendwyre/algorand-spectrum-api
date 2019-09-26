package eosio.spectrum.websocket.api;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
@Component
public class SessionIdAccounts {

    private HashMap<String, HashSet<String>> records;

    public SessionIdAccounts(){
        records = new HashMap<>();
    }

    public synchronized void  addAccount(String sessionId,String account){
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

    public synchronized void removeAccount(String sessionID, String account){
        HashSet accounts = records.get(sessionID);
        accounts.remove(account);
        records.replace(sessionID,accounts);
    }

    public synchronized void removeSession(String session){
        records.remove(session);
    }

    public HashSet getAccounts(String sessionId){
        return this.records.get(sessionId);
    }

    public boolean containsAccount(String account){
        return false;
    }

}
