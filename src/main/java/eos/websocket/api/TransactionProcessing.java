package eos.websocket.api;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;

public class TransactionProcessing {
    private JSONObject transactionMessage;
    private HashSet actionsSet = new HashSet();

    private static final transient Logger logger = LoggerFactory.getLogger(TransactionProcessing.class);

    public TransactionProcessing(JSONObject transactionMessage){
        this.transactionMessage = transactionMessage;
        this.actionsSet.add("eostribeprod");
        this.actionsSet.add("eosiodetroit");
        this.actionsSet.add("eostribe");
        this.actionsSet.add("chainriftcom");
        this.actionsSet.add("eosmechanics");

    }



    public ArrayList<JSONObject> getFiltredActions(){
        ArrayList<JSONObject> actions = new ArrayList<>();
        JSONObject jsonAction = null;
        for (Object action:this.transactionMessage.getJSONObject("trace").getJSONArray("action_traces")){
            if (action instanceof JSONObject){
                jsonAction = (JSONObject)action;
                String actAuthorizationActor;
                String receiptReceiver;
                try {
                    actAuthorizationActor = jsonAction.getJSONObject("act").
                            getJSONArray("authorization").
                            getJSONObject(0).
                            getString("actor");
                    receiptReceiver = jsonAction.getJSONObject("receipt").
                            getString("receiver");

                }catch (JSONException exception){
                    logger.warn("Can't get JSONarray");
                    logger.warn(jsonAction.toString());
                    actAuthorizationActor = "empty";
                    receiptReceiver = "empty";
                }

                if (actionsSet.contains(actAuthorizationActor)){
                    jsonAction.put("block_num", this.transactionMessage.get("block_num"));
                    jsonAction.put("block_timestamp",this.transactionMessage.get("block_timestamp"));
                    jsonAction.put("trx",this.transactionMessage.getJSONObject("trace").get("id"));
                    actions.add(
                            prepareMessage(actAuthorizationActor,jsonAction)
                    );
                    logger.info("actAuthorizationActor is: "+actAuthorizationActor);

                }
                if (actionsSet.contains(receiptReceiver)){
                    jsonAction.put("block_num", this.transactionMessage.get("block_num"));
                    jsonAction.put("block_timestamp",this.transactionMessage.get("block_timestamp"));
                    jsonAction.put("trx",this.transactionMessage.getJSONObject("trace").get("id"));

                    actions.add(
                            prepareMessage(actAuthorizationActor,jsonAction)
                    );
                    logger.info("receiptReceiver is: "+receiptReceiver);
                }


                /**
                 * converting act.data field to string
                 */
//                actionData = jsonAction.getJSONObject("act").get("data").toString();
//                jsonAction.getJSONObject("act").put("data",actionData);

            }else {
                logger.warn("Can't decode action: "+action.toString());
            }
//         actions.add(jsonAction);
        }
        return actions;
    }

    private JSONObject prepareMessage(String accountID, JSONObject action){
        JSONObject message = new JSONObject();
        message.put("accountID",accountID);
        message.put("action",action);
        return message;
    }

    private JSONObject addFields(JSONObject action){

        return new JSONObject();

    }

    public JSONObject getTransaction(){
        String failedDtrxTrace;
        JSONObject transaction = this.transactionMessage;
        /**
         * Converting field failed_dtrx_trace to String
         */
        failedDtrxTrace = this.transactionMessage.
                getJSONObject("trace").
                get("failed_dtrx_trace").toString();
        transaction.getJSONObject("trace").put("failed_dtrx_trace",failedDtrxTrace);
        /**
         * removing filed actions from transaction
         */
        transaction.getJSONObject("trace").remove("actions");
        return transaction;
    }


}
