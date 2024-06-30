package at.technikum.springrestbackend.config;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatHttpConfig {

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> servletContainer() {
        return server -> server.addAdditionalTomcatConnectors(createHttpConnector());
    }

    private Connector createHttpConnector() {
        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setScheme("http");
        connector.setPort(8080); // HTTP port
        connector.setSecure(false);
        connector.setRedirectPort(8443); // HTTPS port
        return connector;
    }
}

