package algorand.spectrum.websocket.api.configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration implements ApplicationContextAware {
    private String redisHostname;
    private String topicTransactions;
    private String topicBlocks = "blocks";
    private String redisBlockChannel;
    private ApplicationContext applicationContext = null;

    @Autowired
    public void setRedisHostname(Properties properties) {
        this.redisHostname = properties.getRedisHostname();
    }
    @Autowired
    public void setTopicTransactions(Properties properties){
        this.topicTransactions = properties.getRedisTransactionsChannel();
    }
    @Autowired
    public void setRedisBlockChannel(Properties properties){
        this.redisBlockChannel = properties.getRedisBlocksChannel();
    }
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisHostname);
        redisStandaloneConfiguration.setPort(6379);
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration);
        return jedisConnectionFactory;
    }
    @Bean
    public StringRedisTemplate redisTemplate() {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }
    @Bean
    public RedisTemplate<String, Object> redisMyTemplate() {
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        // redis serialize
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        RedisTemplate<String,Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        template.setKeySerializer( new StringRedisSerializer() );
        template.setHashKeySerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
//    @Bean
//    public MessageListenerAdapter messageListenerTransactionsHandler() {
//        return new MessageListenerAdapter(applicationContext.getBean("Transactions"));
//    }
    @Bean
    public MessageListenerAdapter messageListenerBlockHandler() {
        return new MessageListenerAdapter(applicationContext.getBean("block"));
    }
    @Bean
    public RedisMessageListenerContainer redisContainer() {
        RedisMessageListenerContainer container
                = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory());
//        container.addMessageListener(messageListenerTransactionsHandler(), topicTransactions());
        container.addMessageListener(messageListenerBlockHandler(), topicBlock());
        return container;
    }
    @Bean
    public ChannelTopic topicTransactions() {
        return new ChannelTopic(topicTransactions);
    }
    @Bean
    public ChannelTopic topicBlocks() {
        return new ChannelTopic(topicBlocks);
    }
    @Bean
    public ChannelTopic topicBlock() {
        return new ChannelTopic(redisBlockChannel);
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
