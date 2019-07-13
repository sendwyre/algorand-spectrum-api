package eos.websocket.api;

public class SubscribeRequest {

    private String accountName;
    private String[] actionNames;

    public SubscribeRequest() {}

    public SubscribeRequest(String accountName) {
        this.accountName = accountName;
        this.actionNames = new String[0];
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setActionNames(String[] actionNames) {
        this.actionNames = actionNames;
    }

    public String[] getActionNames() {
        return this.actionNames;
    }
}
