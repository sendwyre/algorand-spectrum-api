package eos.websocket.api.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:global.properties")
@ConfigurationProperties
public class Properties {
    @Value("${CHRONICLE_WEBSOCKET_PATH}")
    private String chronicleWebsocketPath;

    @Value("${CUSTOMER_WEBSOCKET_PATH}")
    private String customerWebsocketPath;

    public String getCustomerWebsocketPath() {
        return customerWebsocketPath;
    }

    public void setCustomerWebsocketPath(String customerWebsocketPath) {
        this.customerWebsocketPath = customerWebsocketPath;
    }

    public String getChronicleWebsocketPath() {
        return chronicleWebsocketPath;
    }

    public void setChronicleWebsocketPath(String chronicleWebsocketPath) {
        this.chronicleWebsocketPath = chronicleWebsocketPath;
    }
}
