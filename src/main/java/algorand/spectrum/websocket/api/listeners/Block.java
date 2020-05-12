package algorand.spectrum.websocket.api.listeners;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Block {
    private static final transient Logger logger = LoggerFactory.getLogger(Block.class);

    public void handleMessage(String messsage){
//        JSONObject jsonMessage = new JSONObject(messsage);
        logger.info(messsage);
    }
}
