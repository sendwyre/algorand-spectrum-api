package eos.websocket.api;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActionsPublisher implements Observer{

    @Override
    public void handleEvent(ArrayList<JSONObject> actions) {

        System.out.println("called ActionsPubliser");

    }
}
