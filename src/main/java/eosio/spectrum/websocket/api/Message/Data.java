package eosio.spectrum.websocket.api.Message;

import java.util.ArrayList;

public class   Data {
    private String account;
    private ArrayList<String> actions;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public ArrayList<String> getActions() {
        return actions;
    }

    public void setActions(ArrayList<String> actions) {
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
