package algorand.spectrum.websocket.api.requests;

import com.google.gson.annotations.SerializedName;

public enum RequestType {
        @SerializedName("get_blocks")
        get_blocks,
        @SerializedName("get_transactions")
        get_transactions,
        @SerializedName("ping")
        ping
    }
