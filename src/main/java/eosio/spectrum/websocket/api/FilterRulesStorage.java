package eosio.spectrum.websocket.api;

import eosio.spectrum.websocket.api.message.RequestType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;

import static eosio.spectrum.websocket.api.message.RequestType.get_actions;

@Component
public class FilterRulesStorage {

    private HashMap<String,HashSet<String>> filtersGetActions = new HashMap<>();
    private HashMap<String,HashSet<String>> filtersGetTransaction = new HashMap<>();


    public void addRule(String account, HashSet<String> actions, RequestType requestType){
        switch (requestType){
            case get_table_deltas:
                break;
            case get_transaction:
                break;
            case get_blocks:
                break;
            case get_actions:
                if (filtersGetActions.containsKey(account)){
                    HashSet<String> actionsName = filtersGetActions.get(account);
                    for (String actionName:actions){
                        actionsName.add(actionName);
                    }
                    filtersGetActions.replace(account, actionsName);
                }else {
                    filtersGetActions.put(account,actions);
                }
                break;
        }

    }

    public void removeRule(String account, HashSet<String> actions, RequestType requestType){
        switch (requestType){
            case get_table_deltas:
                break;
            case get_transaction:
                break;
            case get_blocks:
                break;
            case get_actions:
                if (filtersGetActions.containsKey(account)) {
                    HashSet<String> actionsName = filtersGetActions.get(account);
                    if (actionsName != null){
                        for (String actionName : actions) {
                            actionsName.remove(actionName);
                        }
                        filtersGetActions.replace(account, actionsName);
                    }else {
                        filtersGetActions.remove(account);
                    }
                }
                break;
        }

    }

    public void removeRule(String account, RequestType requestType){
        switch (requestType){
            case get_table_deltas:
                break;
            case get_transaction:
                break;
            case get_blocks:
                break;
            case get_actions:
                filtersGetActions.remove(account);
                break;
        }
    }

        public HashMap<String, HashSet<String>> getRules(RequestType type){
        HashMap result;
        switch (type){
            case get_actions:
                result= filtersGetActions;
                break;
            case get_blocks:
                result = null;
                break;
            case get_transaction:
                result = null;
                break;
            case get_table_deltas:
                result = null;
                break;
            default:
                result = null;
                break;
        }
        return result;
    }
}
