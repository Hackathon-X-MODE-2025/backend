package dev.zendal.etlsetup.configuration.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Configuration
public class SourceBatchWebClientConfiguration {

    @Bean
    public WebClient sourceBatchWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl(Optional.ofNullable(System.getenv("SOURCE_BATCH_SERVICE_URL")).orElse("localhost:8081"))
                .build();
    }
}
