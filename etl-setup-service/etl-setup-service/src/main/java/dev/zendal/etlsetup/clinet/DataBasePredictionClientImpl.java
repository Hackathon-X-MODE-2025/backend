package dev.zendal.etlsetup.clinet;

import dev.zendal.etlsetup.domain.source.DataBasePrediction;
import dev.zendal.etlsetup.dto.databaseprediction.RecommendRequest;
import dev.zendal.etlsetup.dto.databaseprediction.RecommendationRawResponse;
import dev.zendal.etlsetup.mapper.databaserecomendation.DataBasePredictionMapper;
import dev.zendal.etlsetup.service.session.SessionProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class DataBasePredictionClientImpl implements DataBasePredictionClient {

    private final WebClient dataBasePredictionWebClient;

    private final DataBasePredictionMapper dataBasePredictionMapper;

    private final TranslatorClient translatorClient;

    private final SessionProcessService sessionProcessService;

    @Override
    public DataBasePrediction predict(UUID id, RecommendRequest recommendRequest) {
        return this.dataBasePredictionWebClient.post()
                .uri(uriBuilder -> uriBuilder.path("/recommend-database").build())
                .bodyValue(recommendRequest)
                .retrieve()
                .bodyToMono(RecommendationRawResponse.class)
                .flatMap(v -> this.translate(id, v))
                .map(this.dataBasePredictionMapper::from)
                .block();
    }

    private Mono<RecommendationRawResponse> translate(UUID sessionId, RecommendationRawResponse recommendationRawResponse) {
        List<Mono<Void>> translations = new ArrayList<>();
        this.sessionProcessService.set(sessionId, 75);
        for (RecommendationRawResponse.RecommendedDatabase recommendedDatabase : recommendationRawResponse.getRecommendedDatabases()) {
            translations.add(
                    this.translate(recommendedDatabase::setReason, recommendedDatabase::getReason)
            );
            translations.add(
                    this.translateList(recommendedDatabase::setPros, recommendedDatabase::getPros)
            );
            translations.add(
                    this.translateList(recommendedDatabase::setCons, recommendedDatabase::getCons)
            );
        }

        translations.add(
                this.translateList(recommendationRawResponse::setPerformanceOptimizations,
                        recommendationRawResponse::getPerformanceOptimizations)
        );

        return Mono.when(translations)
                .thenReturn(recommendationRawResponse);
    }

    private Mono<Void> translateList(Consumer<List<String>> setter, Supplier<List<String>> getter) {
        String array = String.join(";", getter.get());

        return this.translate(
                val -> setter.accept(Arrays.stream(val.split(";")).toList()),
                () -> array
        );
    }

    private Mono<Void> translate(Consumer<String> setter, Supplier<String> getter) {
        return this.translatorClient.toRussian(getter.get())
                .doOnNext(setter)
                .then();
    }
}
