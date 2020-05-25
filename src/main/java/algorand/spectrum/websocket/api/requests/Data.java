package algorand.spectrum.websocket.api.requests;

import algorand.spectrum.websocket.api.TxType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Data implements Serializable {
    private String trxAccount;
    private TxType txType;
    private List<String> websocketSessionId;

    public Data(){
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

    public boolean addWebsocketSessionId(String sessionId){
        return websocketSessionId.add(sessionId);
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

    public boolean isValidRule(){
//        if (trxAccount == null){
//            return false;
//        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Data rule = (Data) o;

        return new EqualsBuilder()
                .append(trxAccount, rule.trxAccount)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(trxAccount)
                .toHashCode();
    }
}
