package eosio.spectrum.websocket.api.Message;

import com.google.gson.annotations.SerializedName;

public enum RequestType {
    @SerializedName("get_actions")
    get_actions,
    @SerializedName("get_transaction")
    get_transaction,
    @SerializedName("get_table_deltas")
    get_table_deltas,
    @SerializedName("get_blocsk")
    get_blocks,
    @SerializedName("ping")
    ping
}
