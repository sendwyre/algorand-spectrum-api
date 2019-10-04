package eosio.spectrum.websocket.api;

import com.google.gson.*;
import eosio.spectrum.websocket.api.message.eosio.Transactions;
import eosio.spectrum.websocket.api.message.eosio.Trx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;

public class TransactionsDeserializer implements JsonDeserializer<Transactions> {


    private static final transient Logger logger = LoggerFactory.getLogger(TransactionsDeserializer.class);



    @Override
    public Transactions deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        Transactions transactions = new Transactions();
//        Trx trx = new Trx();
        String trx = new String();
        JsonObject jsonObject = json.getAsJsonObject();
        String status = jsonObject.get("status").getAsString();
        int cpu_usage_us = jsonObject.get("cpu_usage_us").getAsInt();
        int net_usage_words = jsonObject.get("net_usage_words").getAsInt();
        try {
            trx = jsonObject.get("trx").toString();

        }catch (JsonSyntaxException exception){
            logger.warn(exception.toString());
            logger.warn(jsonObject.toString());

        }catch (UnsupportedOperationException exception){
            logger.warn(exception.toString());
            logger.warn(jsonObject.toString());
        }

//        JsonObject trxJsonObject = jsonObject.get("trx").getAsJsonObject();
//        try {
//            String trxSignatures = trxJsonObject.get("signatures").toString();
//            String trxCompression = trxJsonObject.get("compression").getAsString();
//            String trxPacked_context_free_data = trxJsonObject.get("packed_context_free_data").getAsString();
//            String trxPacked_trx = trxJsonObject.get("packed_trx").getAsString();
//            String
//            trx.setSignatures(trxSignatures);
//            trx.setCompression(trxCompression);
//            trx.setPacked_context_free_data(trxPacked_context_free_data);
//            trx.setPacked_trx(trxPacked_trx);
//            transactions.setTrx(trx);
//        }catch (JsonSyntaxException exception){
//            logger.error(exception.toString());
//            logger.warn(jsonObject.toString());
//            transactions.setTrx(null);
//        }

        transactions.setStatus(status);
        transactions.setCpu_usage_us(cpu_usage_us);
        transactions.setNet_usage_words(net_usage_words);
        transactions.setTrx(trx);
        return transactions;
    }
}
