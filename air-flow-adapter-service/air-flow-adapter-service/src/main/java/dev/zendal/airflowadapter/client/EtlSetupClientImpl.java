package dev.zendal.airflowadapter.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.zendal.backend.configuration.WebConstants;

import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EtlSetupClientImpl implements EtlSetupClient {

    private final WebClient etlSetupWebClient;

    @Override
    public void notify(UUID fallbackId, boolean success, UUID airflowId) {
        this.etlSetupWebClient.post()
                .uri(uriBuilder -> uriBuilder.path(WebConstants.FULL_PRIVATE + "/airflow-callback/" + fallbackId).build())
                .bodyValue(Map.of(
                        "success", success,
                        "airflowId", airflowId
                ))
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
