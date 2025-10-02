package dev.zendal.etlsetup.service.source;

import dev.zendal.etlsetup.clinet.SourceBatchClient;
import dev.zendal.etlsetup.domain.source.EtlSessionSourceEntity;
import dev.zendal.etlsetup.domain.source.EtlSessionSourceStatus;
import dev.zendal.etlsetup.dto.request.SourceBatchCallbackRequest;
import dev.zendal.etlsetup.service.source.picker.EtlSessionSourcePickerService;
import dev.zendal.etlsetup.service.source.status.EtlSessionSourceStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EtlSessionSourceProcessingTickerImpl implements EtlSessionSourceProcessingTicker {

    private final EtlSessionSourcePickerService etlSessionSourcePickerService;

    private final EtlSessionSourceStatusService etlSessionSourceStatusService;

    private final SourceBatchClient sourceBatchClient;

    @Override
    @Transactional
    public void find() {
        this.etlSessionSourcePickerService.pickByStatus(EtlSessionSourceStatus.PREPARING).ifPresent(this::process);
    }

    private void process(EtlSessionSourceEntity etlSessionSourceEntity) {
        this.sourceBatchClient.sendProcessing(etlSessionSourceEntity.getId(), etlSessionSourceEntity.getContext());
        //TODO Kafka?
        this.etlSessionSourceStatusService.change(etlSessionSourceEntity.getId(), EtlSessionSourceStatus.PROCESSING);
    }


    @Override
    @Transactional
    public void callback(UUID sourceId, SourceBatchCallbackRequest callbackRequest) {
        this.etlSessionSourcePickerService.pickById(sourceId).setScheme(callbackRequest.getContext());
        this.etlSessionSourceStatusService.change(sourceId, callbackRequest.isSuccess() ? EtlSessionSourceStatus.PROCESSED : EtlSessionSourceStatus.ERROR);
    }
}
