package dev.zendal.etlsetup.configuration.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Configuration
public class AirFlowAdapterWebClientConfiguration {

    @Bean
    public WebClient airFlowAdapterWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl(Optional.ofNullable(System.getenv("AIRFLOW_ADAPTER_SERVICE_URL")).orElse("localhost:8004"))
                .build();
    }
}
