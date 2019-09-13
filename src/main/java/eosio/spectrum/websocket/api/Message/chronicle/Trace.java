package eosio.spectrum.websocket.api.Message.chronicle;

import java.util.List;

public class Trace {
    private Boolean scheduled;
    private String account_ram_delta;
    private int net_usage;
    private List<ActionTraces> action_traces;
    private List<Failed_dtrx_trace> failed_dtrx_trace;
    private int elapsed;
    private int net_usage_words;
    private String except;
    private String error_code;
    private String id;
    private int cpu_usage_us;
    private Partial partial;
    private String status;
}
