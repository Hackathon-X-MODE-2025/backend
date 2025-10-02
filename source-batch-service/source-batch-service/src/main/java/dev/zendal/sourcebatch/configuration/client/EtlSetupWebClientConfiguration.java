package dev.zendal.sourcebatch.configuration.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Configuration
public class EtlSetupWebClientConfiguration {

    @Bean
    public WebClient etlSetupWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl(Optional.ofNullable(System.getenv("ETL_SETUP_SERVICE_URL")).orElse("localhost:8082"))
                .build();
    }
}
