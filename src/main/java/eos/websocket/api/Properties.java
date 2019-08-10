package eos.websocket.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:global.properties")
@ConfigurationProperties
public class Properties {
//    @Value("${ES_TRASNPORT_HOST1}")
//    private String esTransportHost1;
//    @Value("${ES_TRASNPORT_HOST2}")
//    private String esTransportHost2;
    @Value("ES_CLUSTER_NAME")
    private String esClusterName;

//    public String getEsTransportHost1() {
//        return esTransportHost1;
//    }
//
//    public String getEsTransportHost2() {
//        return esTransportHost2;
//    }
//
    public String getEsClusterName() {
        return esClusterName;
    }

    public void setEsClusterName(String esClusterName) {
        this.esClusterName = esClusterName;
    }
}
