package dev.zendal.etlsetup.configuration.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Configuration
public class CodeGeneratorWebClientConfiguration {

    @Bean
    public WebClient codeGeneratorWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl(Optional.ofNullable(System.getenv("CODE_GENERATOR_SERVICE_URL")).orElse("https://fa9f08bafa06.ngrok-free.app"))
                .build();
    }
}
