package eosio.spectrum.websocket.api.message.eosio;

import com.google.gson.Gson;
import eosio.spectrum.websocket.api.message.FilteredAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Transaction {
    private static final transient Logger logger = LoggerFactory.getLogger(Transaction.class);


    private int block_num;
    private String block_timestamp;
    private Trace trace;

    public int getBlock_num() {
        return block_num;
    }

    public void setBlock_num(int block_num) {
        this.block_num = block_num;
    }

    public String getBlock_timestamp() {
        return block_timestamp;
    }

    public void setBlock_timestamp(String block_timestamp) {
        this.block_timestamp = block_timestamp;
    }

    public Trace getTrace() {
        return trace;
    }

    public void setTrace(Trace trace) {
        this.trace = trace;
    }

    public List<ActionTraces> getActions(){
        List<ActionTraces> actionTraces = this.getTrace().getAction_traces();
        for (ActionTraces action:actionTraces) {
            action.setBlock_num(this.getBlock_num());
            action.setBlock_timestamp(this.getBlock_timestamp());
            action.setTrxId(this.getTrace().getId());
        }
        return actionTraces;
    }
    public List<ActionTraces> getActionsFiltered(String actionName){
        List<ActionTraces> actionTraces = new ArrayList<>();
        for (ActionTraces action:this.getTrace().getAction_traces()) {
            if (action.getAct().getName().equals(actionName)){
                action.setBlock_num(this.getBlock_num());
                action.setBlock_timestamp(this.getBlock_timestamp());
                action.setTrxId(this.getTrace().getId());
                actionTraces.add(action);
            }
        }
        return actionTraces;
    }
    public List<FilteredAction> getActionsFiltered(HashMap<String,HashSet<String>> filters){
        List<FilteredAction> filteredActions= new ArrayList<>();
        String actAuthorizationActor;
        String receiptReceiver;
        for (ActionTraces action:this.getTrace().getAction_traces()) {
            try {
                actAuthorizationActor = action.getAct().getAuthorization().get(0).getActor();
            }catch (NullPointerException npe){
                actAuthorizationActor = null;
                logger.warn(new Gson().toJson(action));

            }
            try {
                receiptReceiver = action.getReceipt().getReceiver();
            }catch (NullPointerException npe){
                receiptReceiver = null;
                logger.warn(new Gson().toJson(action));

            }

            if (filters.containsKey(actAuthorizationActor)){
                if (filters.get(actAuthorizationActor).
                        contains(action.getAct().getName())
                         ||
                        (filters.get(actAuthorizationActor.
                                contains(action.getAct().getName()))== null)
                        ){
                    FilteredAction filteredAction = new FilteredAction(actAuthorizationActor,action);
                    filteredActions.add(filteredAction);
                }

            }

            if (filters.containsKey(receiptReceiver)){
                if (filters.get(receiptReceiver).
                        contains(action.getAct().getName())
                        ||
                        (filters.get(receiptReceiver.contains(action.
                                getAct().getName()))== null)
                        ){
                    FilteredAction filteredAction = new FilteredAction(receiptReceiver,action);
                    filteredActions.add(filteredAction);
                }

            }


        }
        return filteredActions;
    }

}
