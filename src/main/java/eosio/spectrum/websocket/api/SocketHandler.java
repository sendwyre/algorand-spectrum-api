package eosio.spectrum.websocket.api;


import com.google.gson.Gson;
import eosio.spectrum.websocket.api.Message.ServiceMessage;
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
import java.util.ArrayList;
import java.util.HashSet;


@Component
@EnableWebSocket
public class SocketHandler extends BinaryWebSocketHandler implements WebSocketHandler {

    private static final transient Logger logger = LoggerFactory.getLogger(SocketHandler.class);

    private ArrayList<JSONObject> actionsList;
    private HashSet<String> accountsFiltered = new HashSet<String>();

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
        TransactionProcessing transactionProcessing;
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
                try {
                    transactionProcessing = new TransactionProcessing(jsonMessage.getJSONObject("data"), getAccountsFiltered());

                    actionsList = transactionProcessing.getFilteredActions();


                    if (actionsList.size() > 0) {
                        for (JSONObject action : actionsList) {
                            redisMessagePublisherActions.publish(action.toString());
                        }

                    }
                    String blockNumber = jsonMessage.
                            getJSONObject("data").
                            getString("block_num");

                    if (Integer.valueOf(blockNumber) % 10 == 0) {
                        session.sendMessage(new BinaryMessage(blockNumber.getBytes()));
                        logger.info("acknowledged block number: " + blockNumber);
                    }
                } catch (JSONException jex) {
                    logger.error("JSON Parse error", jex);
                } catch (IOException ioex) {
                    logger.error("IO Exception", ioex);
                }

                break;

            case "BLOCK_COMPLETED":
                logger.debug("Message type: " + messageType);

                try {
                    String blockNumber = jsonMessage.
                            getJSONObject("data").
                            getString("block_num");

                    session.sendMessage(new BinaryMessage(blockNumber.getBytes()));

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

    public void processServiceMessage(String message) {

        ServiceMessage serviceMessage = new Gson().fromJson(message, ServiceMessage.class);

        switch (serviceMessage.getEvent()) {
            case subscribe:
                switch (serviceMessage.getRequestType()){
                    case get_actions:
                        String account = serviceMessage.getData().getAccount();
                        ArrayList<String> actions = serviceMessage.getData().getActions();
                        getAccountsFiltered().add(serviceMessage.getData().getAccount());
                        break;
                    case get_transaction:
                        break;
                    case get_table_deltas:
                        break;
                    case get_blocks:
                        break;
                }
            case unsubscribe:
                getAccountsFiltered().remove(serviceMessage.getData().getAccount());
        }
    }


    public HashSet<String> getAccountsFiltered() {
        return accountsFiltered;
    }

    public void setAccountsFiltered(HashSet<String> accountsFiltered) {
        this.accountsFiltered = accountsFiltered;
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        System.out.println(status);
    }

}

