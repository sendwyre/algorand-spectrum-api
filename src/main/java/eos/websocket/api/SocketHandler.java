package eos.websocket.api;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;

import org.json.JSONObject;


@Component
@EnableWebSocket
public class SocketHandler extends BinaryWebSocketHandler implements WebSocketHandler{
    private static final transient Logger logger = LoggerFactory.getLogger(SocketHandler.class);
    private JSONObject jsonMessage;
    private String messageType;
    private JSONObject data;
    private ElasticSearchPublisher elasticSearchPublisher;
    private String blockNumber;
    private TransactionProcessing tp;
    {
        try {
            elasticSearchPublisher = new ElasticSearchPublisher();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        logger.info("Session established from: "+session.getRemoteAddress());

    }

    @Override
    public void handleBinaryMessage(WebSocketSession session, BinaryMessage binaryMessage) throws IOException {
        String stringMessage = new String(binaryMessage.getPayload().array(),"UTF-8");
        jsonMessage = new JSONObject(stringMessage);
        messageType = jsonMessage.get("msgtype").toString();
        switch (messageType){
            case "ABI_UPDATED":
                logger.debug("Message type: "+messageType);
                break;
            case "FORK":
                logger.debug("Message type: "+messageType);
                break;
            case "BLOCK":
                logger.debug("Message type: "+messageType);
                break;
            case "TX_TRACE":
                logger.debug("Message type: "+messageType);
                tp = new TransactionProcessing(jsonMessage.getJSONObject("data"));

                elasticSearchPublisher.
                        pubActions(tp.getActions());
                elasticSearchPublisher.
                        pubTransaction(tp.getTransaction());

                blockNumber =  jsonMessage.
                        getJSONObject("data").
                        getString("block_num");

                if (Integer.valueOf(blockNumber) % 100 == 0){
                    session.sendMessage(new BinaryMessage(blockNumber.getBytes()));
                    logger.info("acknowleged block number: "+blockNumber);
                }

                break;
            case "BLOCK_COMPLETED":
                logger.debug("Message type: "+messageType);

                break;
            default:
                logger.debug("Message type undefined: "+messageType);
                break;
        }
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        System.out.println(status);
    }



}
