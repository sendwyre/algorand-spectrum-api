package algorand.spectrum.websocket.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Rule implements Serializable {
    private String trxAccount;
    private TxType txType;
    private List<String> websocketSessionId;

    public Rule(){
        websocketSessionId = new ArrayList<>();
    }

    public String getTrxAccount() {
        return trxAccount;
    }

    public void setTrxAccount(String trxAccount) {
        this.trxAccount = trxAccount;
    }

    public TxType getTxType() {
        return txType;
    }

    public void setTxType(TxType txType) {
        this.txType = txType;
    }

    public void addWebsocketSessionId(String sessionId){
        websocketSessionId.add(sessionId);
    }
    public Boolean delWebsocketSessionId(String sessionId){
        return websocketSessionId.remove(sessionId);
    }

    public List<String> getWebsocketSessionId() {
        return new ArrayList<>(websocketSessionId);

    }

    public void setWebsocketSessionId(List<String> websocketSessionId) {
        this.websocketSessionId = websocketSessionId;
    }
}
