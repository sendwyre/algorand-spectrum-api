package eosio.spectrum.websocket.api;

import eosio.spectrum.websocket.api.message.eosio.ActionTraces;
import eosio.spectrum.websocket.api.message.eosio.Transaction;

import java.util.List;

public class TrxProcessing {
    Transaction transaction;

    public void getActions(){
        List<ActionTraces> actionTraces = this.transaction.getTrace().getAction_traces();
        for (ActionTraces action: actionTraces){
            //TODO: implement action processing
        }

    }
}
