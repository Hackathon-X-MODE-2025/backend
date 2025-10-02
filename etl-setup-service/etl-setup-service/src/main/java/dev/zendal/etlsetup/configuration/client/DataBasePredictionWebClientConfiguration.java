package dev.zendal.etlsetup.configuration.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Configuration
public class DataBasePredictionWebClientConfiguration {

    @Bean
    public WebClient dataBasePredictionWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl(Optional.ofNullable(System.getenv("DATA_BASE_PREDICTION_SERVICE_URL")).orElse("https://f8ac5c765604.ngrok-free.app"))
                .build();
    }
}
