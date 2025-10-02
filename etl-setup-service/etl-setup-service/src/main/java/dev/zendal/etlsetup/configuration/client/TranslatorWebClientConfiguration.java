package dev.zendal.etlsetup.configuration.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Configuration
public class TranslatorWebClientConfiguration {

    @Bean
    public WebClient translatorWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl(Optional.ofNullable(System.getenv("TRANSLATOR_LLM_SERVICE_URL")).orElse("https://f8ac5c765604.ngrok-free.app"))
                .build();
    }
}
