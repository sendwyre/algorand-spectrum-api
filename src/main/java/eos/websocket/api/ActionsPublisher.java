package eos.websocket.api;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class ActionsPublisher implements Observer{

    private static final transient Logger logger = LoggerFactory.getLogger(ActionsPublisher.class);


    @Override
    public void handleEvent(ArrayList<JSONObject> actions) {
        if(!actions.isEmpty()) {
            logger.info(actions.toString());
        }

    }
}
