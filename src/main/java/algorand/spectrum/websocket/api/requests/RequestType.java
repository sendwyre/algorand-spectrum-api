package algorand.spectrum.websocket.api.requests;

import com.google.gson.annotations.SerializedName;

public enum RequestType {
        @SerializedName("get_block")
        get_block,
        @SerializedName("get_transaction")
        get_transaction,
        @SerializedName("ping")
        ping
    }
