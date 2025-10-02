package dev.zendal.etlsetup.clinet;

import dev.zendal.etlsetup.dto.source.DataBaseInitRequest;
import dev.zendal.etlsetup.dto.source.SourceSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.zendal.backend.configuration.WebConstants;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SourceBatchClientImpl implements SourceBatchClient {

    private final WebClient sourceBatchWebClient;

    @Override
    public void sendProcessing(UUID sourceId, SourceSettings settings) {
        this.sourceBatchWebClient.post()
                .uri(uriBuilder -> uriBuilder.path(WebConstants.FULL_PRIVATE + "/batch/" + sourceId).build())
                .bodyValue(settings)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    @Override
    public void initDataBase(DataBaseInitRequest request) {
        this.sourceBatchWebClient.post()
                .uri(uriBuilder -> uriBuilder.path(WebConstants.FULL_PRIVATE + "/databases").build())
                .bodyValue(request)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
