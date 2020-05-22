package algorand.spectrum.websocket.api.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import algorand.spectrum.websocket.api.ws.WebsocketHandlerFrontend;


@Configuration
public class WebSocketConfig implements WebSocketConfigurer {

    private WebsocketHandlerFrontend websocketHandlerFrontend;

    private String frontendWebsocketPath;

    @Autowired
    void setFrontendWebsocketPath(Properties properties) {
        this.frontendWebsocketPath= properties.getFrontendWebsocketPath();
    }
    @Autowired
    public void setWebsocketHandlerFrontend(WebsocketHandlerFrontend websocketHandlerFrontend){
        this.websocketHandlerFrontend = websocketHandlerFrontend;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
         registry.addHandler(websocketHandlerFrontend, frontendWebsocketPath).
                setAllowedOrigins("*");
    }

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(9500000);
        container.setMaxBinaryMessageBufferSize(9500000);
//        WebSocketTransportRegistration webSocketTransportRegistration = new WebSocketTransportRegistration();
//        webSocketTransportRegistration.setMessageSizeLimit();
        return container;
    }


}