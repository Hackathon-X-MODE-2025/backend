package dev.zendal.etlsetup.clinet;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.zendal.backend.configuration.WebConstants;

import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AirFlowAdapterClientImpl implements AirFlowAdapterClient {

    private final WebClient airFlowAdapterWebClient;

    @Override
    public void saveDag(UUID requestId, String dagCode) {
        this.airFlowAdapterWebClient.post()
                .uri(uriBuilder -> uriBuilder.path(WebConstants.FULL_PRIVATE + "/dags").build())
                .bodyValue(Map.of(
                        "fallbackId", requestId.toString(),
                        "dagCode", dagCode
                ))
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
