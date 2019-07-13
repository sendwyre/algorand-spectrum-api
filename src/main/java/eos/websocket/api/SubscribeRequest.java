package eos.websocket.api;

public class SubscribeRequest {

    private String[] accounts;
    private String[] actionNames;

    public SubscribeRequest() {
    }

    public void setAccounts(String[] accounts) {
        this.accounts = accounts;
    }

    public String[] getAccounts() {
        return accounts;
    }

    public void setActionNames(String[] actionNames) {
        this.actionNames = actionNames;
    }

    public String[] getActionNames() {
        return this.actionNames;
    }
}
