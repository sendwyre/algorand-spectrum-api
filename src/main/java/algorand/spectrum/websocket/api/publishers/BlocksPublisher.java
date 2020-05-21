package algorand.spectrum.websocket.api.publishers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Component
public class BlocksPublisher implements MessagePublisher {

        private final RedisTemplate redisTemplate;
        private final ChannelTopic topicBlocks;

        @Autowired
        public BlocksPublisher(RedisTemplate redisTemplate,
                               ChannelTopic topicBlocks) {
            this.redisTemplate = redisTemplate;
            this.topicBlocks = topicBlocks;
        }

        @Override
        public void publish(String message) {
            redisTemplate.convertAndSend(topicBlocks.getTopic(), message);
        }
}

