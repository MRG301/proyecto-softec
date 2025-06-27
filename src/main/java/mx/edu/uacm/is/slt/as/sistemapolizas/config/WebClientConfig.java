package mx.edu.uacm.is.slt.as.sistemapolizas.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${remote.base-url:http://nachintoch.mx:8080}")
    private String baseUrl;

    @Bean
    WebClient polizaWebClient() {
        return WebClient.builder().baseUrl(baseUrl).build();
    }
}
