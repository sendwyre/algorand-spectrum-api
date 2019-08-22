package eos.websocket.api;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public interface Observer {
    public void handleEvent(ArrayList<JSONObject> actions);


}
