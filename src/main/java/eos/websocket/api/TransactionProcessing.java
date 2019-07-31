package eos.websocket.api;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class TransactionProcessing {
    private JSONObject transactionMessage;
    private ArrayList<JSONObject> actions;
    private JSONObject transaction;

    private static final transient Logger logger = LoggerFactory.getLogger(TransactionProcessing.class);

    public TransactionProcessing(JSONObject transactionMessage){
        this.transactionMessage = transactionMessage;
    }

    public ArrayList<JSONObject> getActions(){
        actions = new ArrayList<>();
        JSONObject jsonAction = null;
        String actionData;
        for (Object action:this.transactionMessage.getJSONObject("trace").getJSONArray("action_traces")){
            if (action instanceof JSONObject){
                jsonAction = (JSONObject)action;
                jsonAction.put("block_num",this.transactionMessage.get("block_num"));
                jsonAction.put("bloc_timestamp",this.transactionMessage.get("block_timestamp"));
                jsonAction.put("trx",this.transactionMessage.getJSONObject("trace").get("id"));
                /**
                 * converting act.data field to string
                 */
                actionData = jsonAction.getJSONObject("act").getJSONObject("data").toString();
                jsonAction.getJSONObject("act").put("data",actionData);
            }else {
                logger.error("Can't decode action");
                logger.error(action.toString());
            }
         actions.add(jsonAction);
        }
        return this.actions;
    }

    public JSONObject getTransaction(){
        String failedDtrxTrace;
        transaction = this.transactionMessage;
        /**
         * Converting field failed_dtrx_trace to String
         */
        failedDtrxTrace = this.transactionMessage.
                getJSONObject("trace").
                get("failed_dtrx_trace").toString();
        this.transaction.getJSONObject("trace").put("failed_dtrx_trace",failedDtrxTrace);
        /**
         * removing filed actions from transaction
         */
        this.transaction.getJSONObject("trace").remove("actions");
        return this.transaction;
    }


}
