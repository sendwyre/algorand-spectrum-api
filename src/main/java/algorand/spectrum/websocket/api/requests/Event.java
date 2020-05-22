package algorand.spectrum.websocket.api.requests;

import com.google.gson.annotations.SerializedName;

public enum Event {
    @SerializedName("subscribe")
    subscribe,
    @SerializedName("unsubscribe")
    unsubscribe

}
