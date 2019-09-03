package eos.websocket.api.configuration;

import eos.websocket.api.CustomersWebSocketHandler;
import eos.websocket.api.SocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;


@Configuration
public class WebSocketConfig implements WebSocketConfigurer {

    private SocketHandler socketHandler;

    private CustomersWebSocketHandler custormersWebSocketHandler;

    private String chronicleWebsocketPath;
    private String customerWebsocketPath;

    @Autowired
    public void setChronicleWebsocketPath(Properties properties){
        this.chronicleWebsocketPath = properties.getChronicleWebsocketPath();
    }

    @Autowired void setCustomerWebsocketPath(Properties properties){
        this.customerWebsocketPath = properties.getCustomerWebsocketPath();
    }

    @Autowired
    public void setSocketHandler(SocketHandler socketHandler) {
        this.socketHandler = socketHandler;
    }

    @Autowired
    public void setCustormersWebSocketHandler(CustomersWebSocketHandler custormersWebSocketHandler){
        this.custormersWebSocketHandler = custormersWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(socketHandler, chronicleWebsocketPath);
        registry.addHandler(custormersWebSocketHandler,customerWebsocketPath);
    }


    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(1500000);
        container.setMaxBinaryMessageBufferSize(1500000);
        return container;
    }

}