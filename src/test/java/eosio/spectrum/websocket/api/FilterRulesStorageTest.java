package eosio.spectrum.websocket.api;

import eosio.spectrum.websocket.api.message.Data;
import eosio.spectrum.websocket.api.message.Event;
import eosio.spectrum.websocket.api.message.RequestType;
import eosio.spectrum.websocket.api.message.ServiceMessage;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


import java.util.HashSet;

public class FilterRulesStorageTest {

    private FilterRulesStorage filterRulesStorage;
    private ServiceMessage serviceMessageFirst;
    private ServiceMessage serviceMessageSecond;
    private String firstAccount;
    private String firstAccountAction_1;
    private String firstAccountAction_2;
    private HashSet<String> firstAccountActions;

    private String secondAccount;
    private String secondAccountActions_1;
    private String secondAccountActions_2;
    private HashSet<String> secondAccountActions;


    @Before
    public void init(){
        filterRulesStorage = new FilterRulesStorage();
        serviceMessageFirst = new ServiceMessage();
        serviceMessageSecond = new ServiceMessage();

        serviceMessageFirst.setEvent(Event.subscribe);
        serviceMessageFirst.setRequestType(RequestType.get_actions);
        Data firstAccountData = new Data();
        firstAccount = "firstAccount";
        firstAccountAction_1 = "firstAccountAction_1";
        firstAccountAction_2 = "firstAccountAction_2";
        firstAccountActions = new HashSet<>();
        firstAccountActions.add(firstAccountAction_1);
        firstAccountActions.add(firstAccountAction_2);
        firstAccountData.setAccount(firstAccount);
        firstAccountData.setActions(firstAccountActions);
        serviceMessageFirst.setData(firstAccountData);

        secondAccountActions_1 = "secondAccountActions_1";
        secondAccountActions_2 = "secondAccountActions_2";
        secondAccountActions = new HashSet<>();
        secondAccountActions.add(secondAccountActions_1);
        secondAccountActions.add(secondAccountActions_2);
        Data secondAccountData = new Data();
        secondAccount ="secondAccount";
        secondAccountData.setAccount(secondAccount);
        secondAccountData.setActions(secondAccountActions);
        serviceMessageSecond.setEvent(Event.subscribe);
        serviceMessageSecond.setRequestType(RequestType.get_actions);
        serviceMessageSecond.setData(secondAccountData);





    }
    @Test
    public void addRuleGetActions(){
        filterRulesStorage.addRule(serviceMessageFirst.getData().getAccount(), serviceMessageFirst.getData().getActions(), serviceMessageFirst.getRequestType());
        filterRulesStorage.addRule(serviceMessageSecond.getData().getAccount(), serviceMessageSecond.getData().getActions(), serviceMessageSecond.getRequestType());

        assertTrue(filterRulesStorage.getRules(RequestType.get_actions).containsKey(firstAccount));
        assertTrue(filterRulesStorage.getRules(RequestType.get_actions).get(firstAccount).contains(firstAccountAction_1));
        assertTrue(filterRulesStorage.getRules(RequestType.get_actions).get(firstAccount).contains(firstAccountAction_2));
        assertEquals(firstAccountActions, filterRulesStorage.getRules(RequestType.get_actions).get(firstAccount));
        assertEquals(secondAccountActions, filterRulesStorage.getRules(RequestType.get_actions).get(secondAccount));
    }
    @Test
    public void removeRuleGetActions(){
        filterRulesStorage.addRule(serviceMessageFirst.getData().getAccount(), serviceMessageFirst.getData().getActions(), serviceMessageFirst.getRequestType());
        filterRulesStorage.addRule(serviceMessageSecond.getData().getAccount(), serviceMessageSecond.getData().getActions(), serviceMessageSecond.getRequestType());
        filterRulesStorage.removeRule(serviceMessageFirst.getData().getAccount(), serviceMessageFirst.getRequestType());
        assertEquals(secondAccountActions, filterRulesStorage.getRules(RequestType.get_actions).get(secondAccount));
        assertFalse(filterRulesStorage.getRules(RequestType.get_actions).containsKey(firstAccount));
    }

}
