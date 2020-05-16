package algorand.spectrum.websocket.api.listeners;

import algorand.spectrum.websocket.api.RestClient;
import algorand.spectrum.websocket.api.TxProcessing;
import algorand.spectrum.websocket.api.TxType;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Component
public class Block {
    private static final transient Logger logger = LoggerFactory.getLogger(Block.class);
    private RestClient restClient;

    @Autowired
    public void setRestClient(RestClient restClient){
        this.restClient = restClient;
    }

    public void handleMessage(String messageBlock){
        String block = restClient.getBlock(messageBlock);
        JSONObject jsonBlock = new JSONObject(block);
        JSONObject txns = jsonBlock.getJSONObject("txns");
        if (txns.length() >0 ){
            TxProcessing txProcessing;
            JSONArray trxs = txns.getJSONArray("transactions");
            for (Object trx:trxs) {
                TxType txType = getTxType((JSONObject) trx);
                getTxAccounts((JSONObject)trx,txType);
            }
            logger.info(trxs.toString());
        }
    }

    private TxType getTxType(JSONObject json){
        switch (json.getString("type")){
            case ("pay"):
                return TxType.pay;
            case ("keyreg"):
                return TxType.keyreg;
            case ("acfg"):
                return TxType.acfg;
            case ("axfer"):
                return TxType.axfer;
            case ("afrz"):
                return TxType.afrz;
            default:
                return TxType.unknown;
        }
    }
    private Set getTxAccounts(JSONObject json, TxType txType){
        switch (txType){
            case pay:
                Set<String> accounts = new HashSet<>();
                accounts.add(json.getString("from"));
                accounts.add(json.getJSONObject("payment").getString("to"));
                return accounts;
            default:
                return new HashSet();
        }
    }
}
