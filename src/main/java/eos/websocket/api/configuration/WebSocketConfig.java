package eos.websocket.api.configuration;

import eos.websocket.api.SocketHandlerFrontend;
import eos.websocket.api.SocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;


@Configuration
public class WebSocketConfig implements WebSocketConfigurer {

    private SocketHandler socketHandler;

    private SocketHandlerFrontend socketHandlerFrontend;

    private String socketPath;
    private String socketPathFrontend;

    @Autowired
    public void setSocketPath(Properties properties){
        this.socketPath = properties.getSocketPath();
    }

    @Autowired void setSocketPathFrontend(Properties properties){
        this.socketPathFrontend = properties.getSocketPathFrontend();
    }

    @Autowired
    public void setSocketHandler(SocketHandler socketHandler) {
        this.socketHandler = socketHandler;
    }

    @Autowired
    public void setSocketHandlerFrontend(SocketHandlerFrontend socketHandlerFrontend){
        this.socketHandlerFrontend = socketHandlerFrontend;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(socketHandler, socketPath);
        registry.addHandler(socketHandlerFrontend, socketPathFrontend);
    }


    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(1500000);
        container.setMaxBinaryMessageBufferSize(1500000);
        return container;
    }

}