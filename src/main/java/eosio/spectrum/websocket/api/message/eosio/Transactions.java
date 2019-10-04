package eosio.spectrum.websocket.api.message.eosio;


public class Transactions {
    private String status;
    private int cpu_usage_us;
    private int net_usage_words;
    private String trx;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCpu_usage_us() {
        return cpu_usage_us;
    }

    public void setCpu_usage_us(int cpu_usage_us) {
        this.cpu_usage_us = cpu_usage_us;
    }

    public int getNet_usage_words() {
        return net_usage_words;
    }

    public void setNet_usage_words(int net_usage_words) {
        this.net_usage_words = net_usage_words;
    }

    public String getTrx() {
        return trx;
    }

    public void setTrx(String trx) {
        this.trx = trx;
    }
}
