package dev.zendal.etlsetup.clinet;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class TranslatorClientImpl implements TranslatorClient {

    private final WebClient translatorWebClient;

    @Override
    public Mono<String> toRussian(String text) {
        return this.translatorWebClient.post()
                .uri(uriBuilder -> uriBuilder.path("/en-to-ru").build())
                .bodyValue(
                        Map.of(
                                "text", text,
                                "context_type", "general"
                        )
                ).retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, String>>() {
                })
                .map(v -> v.getOrDefault("translated_text", text))
                .map(this::preprocess);
    }

    @Override
    public Mono<String> toEnglish(String text) {
        return this.translatorWebClient.post()
                .uri(uriBuilder -> uriBuilder.path("/ru-to-en").build())
                .bodyValue(
                        Map.of(
                                "text", text,
                                "context_type", "general"
                        )
                ).retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, String>>() {
                })
                .map(v -> v.getOrDefault("translated_text", text))
                .map(this::preprocess);
    }

    private String preprocess(String text) {
       return text.replaceAll("Вы можете перевести этот текст следующим образом: ", "");
    }
}
