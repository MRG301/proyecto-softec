package mx.edu.uacm.is.slt.as.sistemapolizas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    WebClient polizaWebClient(@Value("${remote.base-url:http://nachintoch.mx:8080}") String baseUrl) {
        return WebClient.builder().baseUrl(baseUrl).build();
    }
}
