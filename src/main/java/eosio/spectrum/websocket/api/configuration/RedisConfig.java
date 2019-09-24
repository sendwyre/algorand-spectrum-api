package eosio.spectrum.websocket.api.configuration;


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
public class RedisConfig implements ApplicationContextAware {

    private final String ACTIONS_CHANNEL = "actions";
    private final String SERVICE_CHANNEL = "service";

    private String redisHostname;

    private ApplicationContext applicationContext = null;

    @Autowired
    public void setRedisHostname(Properties properties) {
        this.redisHostname = properties.getRedisHostname();
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
    public MessageListenerAdapter messageListenerSocketHandler() {
        return new MessageListenerAdapter(applicationContext.getBean("socketHandler"));
    }

    @Bean
    public MessageListenerAdapter messageListenerSocketHandlerFrontend() {
        return new MessageListenerAdapter(applicationContext.getBean("socketHandlerFrontend"));
    }

    @Bean
    public RedisMessageListenerContainer redisContainer() {
        RedisMessageListenerContainer container
                = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory());
        container.addMessageListener(messageListenerSocketHandler(), topicService());
        container.addMessageListener(messageListenerSocketHandlerFrontend(), topicActions());
        return container;
    }

    @Bean
    public ChannelTopic topicActions() {
        return new ChannelTopic(ACTIONS_CHANNEL);
    }

    @Bean
    public ChannelTopic topicService() {
        return new ChannelTopic(SERVICE_CHANNEL);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
