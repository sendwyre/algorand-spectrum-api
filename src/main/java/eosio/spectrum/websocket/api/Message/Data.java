package eosio.spectrum.websocket.api.message;

import java.util.HashSet;

public class   Data {
    private String account;
    private HashSet<String> actions;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public HashSet<String> getActions() {
        return actions;
    }

    public void setActions(HashSet actions) {
            this.actions = actions;

    }

    @Override
    public String toString() {
        return "Data {" +
                "account='" + account + '\'' +
                ", actions=" + actions +
                '}';
    }
}
