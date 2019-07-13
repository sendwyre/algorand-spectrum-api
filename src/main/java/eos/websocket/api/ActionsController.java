package eos.websocket.api;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import org.apache.kafka.clients.producer.Producer;



@Controller
public class ActionsController {


    @MessageMapping("/subscribe")
    @SendTo("/topic/accounts")
    public AccountResponse setAccount(SubscribeRequest request) throws Exception {
        Producer<String, String> producer = KafkaService.createProducer();
        long time = System.currentTimeMillis();
        try {
                String accountName = request.getAccountName();
                final ProducerRecord<String, String> record =
                        new ProducerRecord<>("accounts", "account_name", accountName);

                RecordMetadata metadata = producer.send(record).get();

                long elapsedTime = System.currentTimeMillis() - time;
                System.out.printf("sent record(key=%s value=%s) " +
                                "meta(partition=%d, offset=%d) time=%d\n",
                        record.key(), record.value(), metadata.partition(),
                        metadata.offset(), elapsedTime);

        } catch(Exception ex) {
            System.err.print(ex);
            throw ex;
        }
        finally {
            producer.flush();
            producer.close();
        }

        return new AccountResponse("Account response: " + HtmlUtils.htmlEscape(request.getAccountName()));
    }

}
