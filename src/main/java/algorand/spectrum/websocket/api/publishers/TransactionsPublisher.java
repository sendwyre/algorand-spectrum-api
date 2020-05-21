package algorand.spectrum.websocket.api.publishers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Component
public class TransactionsPublisher implements MessagePublisher {

        private final RedisTemplate redisTemplate;
        private final ChannelTopic topicTransactions;

        @Autowired
        public TransactionsPublisher(RedisTemplate redisTemplate,
                                                ChannelTopic topicTransactions) {
            this.redisTemplate = redisTemplate;
            this.topicTransactions = topicTransactions;
        }

        @Override
        public void publish(String message) {
            redisTemplate.convertAndSend(topicTransactions.getTopic(), message);
        }
}

