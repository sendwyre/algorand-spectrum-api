package eosio.spectrum.websocket.api;

import eosio.spectrum.websocket.api.Message.Account_ram_deltas;
import eosio.spectrum.websocket.api.Message.Act;
import eosio.spectrum.websocket.api.Message.Receipt;

import java.util.List;

public class ActionTraces {
    private String console;
    private int creator_action_ordinal;
    private String receiver;
    private List<Account_ram_deltas> account_ram_deltas;
    private Boolean context_free;
    private int elapsed;
    private int block_num;
    private Act act;
    private int action_ordinal;
    private String block_timestamp;
    private Receipt receipt;
    private String except;
    private String error_code;


}
