package algorand.spectrum.websocket.api.configuration;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class RedisConfiguration implements ApplicationContextAware {
    private String redisHostname;
    private String redisTransactionsChannel;
    private String redisBlockChannel;
    private ApplicationContext applicationContext = null;

    @Autowired
    public void setRedisHostname(Properties properties) {
        this.redisHostname = properties.getRedisHostname();
    }
    @Autowired
    public void setRedisTransactionsChannel(Properties properties){
        this.redisTransactionsChannel = properties.getRedisTransactionsChannel();
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
//    @Bean
//    public MessageListenerAdapter messageListenerTransactionsHandler() {
//        return new MessageListenerAdapter(applicationContext.getBean("transactions"));
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
        return new ChannelTopic(redisTransactionsChannel);
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
