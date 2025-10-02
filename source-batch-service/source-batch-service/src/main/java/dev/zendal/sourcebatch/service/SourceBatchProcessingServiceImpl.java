package dev.zendal.sourcebatch.service;

import dev.zendal.sourcebatch.client.EtlSetupClient;
import dev.zendal.sourcebatch.dto.etlsetup.SourceBatchCallbackRequest;
import dev.zendal.sourcebatch.dto.source.SourceSettings;
import dev.zendal.sourcebatch.service.handler.SourceBatchHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SourceBatchProcessingServiceImpl implements SourceBatchProcessingService {

    private final List<SourceBatchHandler> handlers;

    private final EtlSetupClient etlSetupClient;

    @Async
    @Override
    public void process(UUID sourceId, SourceSettings sourceSettings) {
        boolean isComplete = false;
        try {
            final var result = this.unsafeHandle(sourceSettings);

            this.etlSetupClient.notify(sourceId, new SourceBatchCallbackRequest().setSuccess(true).setContext(result));
            isComplete = true;
        } catch (Exception e) {
            this.etlSetupClient.notify(sourceId, new SourceBatchCallbackRequest().setSuccess(false).setContext(e.getMessage()));
            isComplete = true;
        } finally {
            if (!isComplete) {
                this.etlSetupClient.notify(sourceId, new SourceBatchCallbackRequest().setSuccess(false).setContext("Finally block...."));
            }
        }
    }

    private String unsafeHandle(SourceSettings sourceSettings) {
        final var pickedHandler = this.handlers.stream().filter(handler -> handler.type().equals(sourceSettings.type())).findFirst().orElseThrow(
                () -> new IllegalArgumentException("Source type not found!" + sourceSettings.type())
        );
        log.info("Picked {} handler", pickedHandler.getClass().getSimpleName());
        return pickedHandler.process(sourceSettings);
    }
}
