package eos.websocket.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class RedisMessagePublisher implements MessagePublisher{


    private final StringRedisTemplate stringRedisTemplate;
    private final ChannelTopic pubTopic;


    @Autowired
    public RedisMessagePublisher(StringRedisTemplate stringRedisTemplate,
                     ChannelTopic pubTopic) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.pubTopic = pubTopic;
    }


    @Override
    public void publish(String message) {
        stringRedisTemplate.convertAndSend(pubTopic.getTopic(), message);
    }
}
