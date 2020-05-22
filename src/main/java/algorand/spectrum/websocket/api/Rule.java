package algorand.spectrum.websocket.api;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Rule rule = (Rule) o;

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
