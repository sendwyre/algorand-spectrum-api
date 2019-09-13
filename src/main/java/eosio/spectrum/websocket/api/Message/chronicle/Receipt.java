package eosio.spectrum.websocket.api.Message.chronicle;

import java.util.List;

public class Receipt {
    private String receiver;
    private int code_sequence;
    private int abi_sequence;
    private int recv_sequence;
    private List<Auth_sequence> auth_sequence;
    private String act_digest;
    private int global_sequence;



}
