package dev.zendal.etlsetup.service.source;

import dev.zendal.etlsetup.dto.request.SourceBatchCallbackRequest;

import java.util.UUID;

public interface EtlSessionSourceProcessingTicker {

    void find();

    void callback(UUID sourceId, SourceBatchCallbackRequest callbackRequest);
}
