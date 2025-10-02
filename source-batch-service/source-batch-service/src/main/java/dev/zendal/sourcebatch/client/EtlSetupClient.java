package dev.zendal.sourcebatch.client;

import dev.zendal.sourcebatch.dto.etlsetup.SourceBatchCallbackRequest;

import java.util.UUID;

public interface EtlSetupClient {

    void notify(UUID sourceId, SourceBatchCallbackRequest sourceBatchCallbackRequest);
}
