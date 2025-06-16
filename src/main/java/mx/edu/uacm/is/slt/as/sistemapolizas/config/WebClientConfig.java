package mx.edu.uacm.is.slt.as.sistemapolizas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    WebClient polizaWebClient() {
        return WebClient.builder().baseUrl("http://nachintoch.mx:8080").build();
    }
}
