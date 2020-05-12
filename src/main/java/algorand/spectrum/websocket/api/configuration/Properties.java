package algorand.spectrum.websocket.api.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
@ConfigurationProperties
public class Properties {
    @Value("${CUSTOMER_WEBSOCKET_PATH}")
    private String customerWebsocketPath;
    @Value("${REDIS_HOSTNAME}")
    private String redisHostname;
    @Value("${REDIS_TRANSACTIONS_CHANNEL}")
    private String redisTransactionsChannel;

    @Value("${REDIS_BLOCKS_CHANNEL}")
    private String redisBlocksChannel;

    public String getRedisBlocksChannel() {
        return redisBlocksChannel;
    }

    public void setRedisBlocksChannel(String redisBlocksChannel) {
        this.redisBlocksChannel = redisBlocksChannel;
    }

    public String getCustomerWebsocketPath() {
        return customerWebsocketPath;
    }

    public void setCustomerWebsocketPath(String customerWebsocketPath) {
        this.customerWebsocketPath = customerWebsocketPath;
    }

    public String getRedisHostname() {
        return redisHostname;
    }

    public void setRedisHostname(String redisHostname) {
        this.redisHostname = redisHostname;
    }

    public String getRedisTransactionsChannel() {
        return redisTransactionsChannel;
    }

    public void setRedisTransactionsChannel(String redisTransactionsChannel) {
        this.redisTransactionsChannel = redisTransactionsChannel;
    }
}