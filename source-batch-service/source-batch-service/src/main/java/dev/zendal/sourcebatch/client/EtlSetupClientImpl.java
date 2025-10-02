package dev.zendal.sourcebatch.client;

import dev.zendal.sourcebatch.dto.etlsetup.SourceBatchCallbackRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.zendal.backend.configuration.WebConstants;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EtlSetupClientImpl implements EtlSetupClient {

    private final WebClient etlSetupWebClient;

    @Override
    public void notify(UUID sourceId, SourceBatchCallbackRequest sourceBatchCallbackRequest) {
        this.etlSetupWebClient.post()
                .uri(uriBuilder -> uriBuilder.path(WebConstants.FULL_PRIVATE + "/batch-callback/" + sourceId).build())
                .bodyValue(sourceBatchCallbackRequest)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
