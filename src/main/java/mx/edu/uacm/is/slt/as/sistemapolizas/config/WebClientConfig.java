package mx.edu.uacm.is.slt.as.sistemapolizas.config;

import org.springframework.beans.factory.annotation.Value;
    @Value("${remote.base-url:http://nachintoch.mx:8080}")
    private String baseUrl;

    public WebClient polizaWebClient() {
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    WebClient polizaWebClient(@Value("${remote.base-url}") String baseUrl) {
      develop
        return WebClient.builder().baseUrl(baseUrl).build();
    }
}
