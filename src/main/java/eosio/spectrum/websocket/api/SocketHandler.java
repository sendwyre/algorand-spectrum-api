package eosio.spectrum.websocket.api;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eosio.spectrum.websocket.api.message.FilteredAction;
import eosio.spectrum.websocket.api.message.ServiceMessage;
import eosio.spectrum.websocket.api.message.chronicle.TX_TRACE;
import eosio.spectrum.websocket.api.message.eosio.ActionTraces;
import eosio.spectrum.websocket.api.message.eosio.Transaction;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


@Component
@EnableWebSocket
public class SocketHandler extends BinaryWebSocketHandler implements WebSocketHandler {

    private static final transient Logger logger = LoggerFactory.getLogger(SocketHandler.class);

    private HashMap<String,HashSet<String>> get_actionsFilters = new HashMap<>();

    private RedisMessagePublisherActions redisMessagePublisherActions;

    @Autowired
    public void setRedisMessagePublisherActions(RedisMessagePublisherActions redisMessagePublisherActions) {
        this.redisMessagePublisherActions = redisMessagePublisherActions;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        logger.info("Session established from: " + session.getRemoteAddress());
    }

    @Override
    public void handleBinaryMessage(WebSocketSession session, BinaryMessage binaryMessage) throws UnsupportedEncodingException, JSONException {

        String stringMessage = new String(binaryMessage.getPayload().array(), "UTF-8");

        JSONObject jsonMessage = new JSONObject(stringMessage);
        String messageType = jsonMessage.get("msgtype").toString();
        switch (messageType) {
            case "ABI_UPDATED":
                logger.debug("Message type: " + messageType);
                break;
            case "FORK":
                logger.debug("Message type: " + messageType);
                break;
            case "TABLE_DELTAS":
                logger.debug("Message type: " + messageType);
                break;
            case "BLOCK":
                logger.debug("Message type: " + messageType);
                break;
            case "TX_TRACE":
                logger.debug("Message type: " + messageType);
                    TX_TRACE tx_trace = new Gson().fromJson(stringMessage, TX_TRACE.class);

                    Transaction transaction = tx_trace.getTransaction();
                    List<FilteredAction> filteredActions = transaction.getActionsFiltered(get_actionsFilters);
                    if ( filteredActions.size() > 0) {
                        for (FilteredAction filteredAction : filteredActions) {
                            redisMessagePublisherActions.publish(
                                    new Gson().toJson(filteredAction));
                        }
                    }
                break;
            case "BLOCK_COMPLETED":
                try {
                    String blockNumber = jsonMessage.
                            getJSONObject("data").
                            getString("block_num");
                    logger.debug("acknowleged block number: : " + blockNumber);
                    if (Integer.valueOf(blockNumber) % 100==0){
                        logger.info("acknowleged block number: "+blockNumber);
                    }
                    session.sendMessage(new BinaryMessage(blockNumber.getBytes()));
//                    if (transaction.getBlock_num() % 10 == 0){
//                        if (session.isOpen()) {
//                            BigInteger bigInt = BigInteger.valueOf(transaction.getBlock_num());
//                            session.sendMessage(new BinaryMessage(bigInt.toByteArray()));
//                            logger.info("acknowleged block number: " + transaction.getBlock_num());
//                        }
//                    }

                } catch (JSONException jex) {
                    logger.error("JSON Parse error", jex);
                } catch (IOException ioex) {
                    logger.error("IO Exception", ioex);
                }
                break;
            default:
                logger.debug("Unknown message type: " + messageType);
                break;
        }
    }

    public void handleMessage(String message) {
        logger.info(message);
        processServiceMessage(message);
    }

    private void processServiceMessage(String message) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        ServiceMessage serviceMessage = gson.fromJson(message, ServiceMessage.class);

        switch (serviceMessage.getEvent()) {
            case subscribe:
                switch (serviceMessage.getRequestType()){
                    case get_actions:
                        getGet_actionsFilters().put(
                                serviceMessage.getData().getAccount(),
                                serviceMessage.getData().getActions()
                        );
                        break;
                    case get_transaction:
                        break;
                    case get_table_deltas:
                        break;
                    case get_blocks:
                        break;
                }
                break;
            case unsubscribe:
                switch (serviceMessage.getRequestType()){
                    case get_actions:
                        getGet_actionsFilters().remove(serviceMessage.getData().getAccount());
                        break;
                }

                break;
        }
    }


    private HashMap<String, HashSet<String>> getGet_actionsFilters(){
        return this.get_actionsFilters;
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        logger.info("Chronicle was disconnected "+status.getReason());
    }

}

