package eosio.spectrum.websocket.api.Message;

import com.google.gson.annotations.SerializedName;

public enum Event {
    @SerializedName("subscribe")
    subscribe,
    @SerializedName("unsubscribe")
    unsubscribe

}
