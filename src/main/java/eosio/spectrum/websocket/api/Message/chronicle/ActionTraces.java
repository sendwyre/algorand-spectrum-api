package eosio.spectrum.websocket.api.Message.chronicle;

import eosio.spectrum.websocket.api.Message.chronicle.Account_ram_deltas;
import eosio.spectrum.websocket.api.Message.chronicle.Act;
import eosio.spectrum.websocket.api.Message.chronicle.Receipt;

import java.util.List;

public class ActionTraces {
    private Boolean context_free;
    private int elapsed;
    private String console;
    private Act act;
    private int creator_action_ordinal;
    private String receiver;
    private int action_ordinal;
    private Receipt receipt;
    private String except;
    private String error_code;
    private List<Account_ram_deltas> account_ram_deltas;

    private int block_num;
    private String block_timestamp;
    private String trxId;


}
