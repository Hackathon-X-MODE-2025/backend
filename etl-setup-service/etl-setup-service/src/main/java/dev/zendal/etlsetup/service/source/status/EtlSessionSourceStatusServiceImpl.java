package dev.zendal.etlsetup.service.source.status;

import dev.zendal.etlsetup.domain.source.EtlSessionSourceStatus;
import dev.zendal.etlsetup.service.source.picker.EtlSessionSourcePickerService;
import dev.zendal.etlsetup.service.source.status.processor.EtlSessionSourceStatusPostProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EtlSessionSourceStatusServiceImpl implements EtlSessionSourceStatusService {

    private static final Map<EtlSessionSourceStatus, Set<EtlSessionSourceStatus>> STATUS_FLOW = Map.of(
            EtlSessionSourceStatus.PREPARING, Set.of(EtlSessionSourceStatus.PROCESSING),
            EtlSessionSourceStatus.PROCESSING, Set.of(EtlSessionSourceStatus.PROCESSED, EtlSessionSourceStatus.ERROR)
    );

    private final EtlSessionSourcePickerService etlSessionSourcePickerService;

    private final List<EtlSessionSourceStatusPostProcessor> postProcessors;

    @Override
    @Transactional
    public void change(UUID sessionSourceId, EtlSessionSourceStatus newStatus) {
        final var source = this.etlSessionSourcePickerService.pickById(sessionSourceId);

        if (!STATUS_FLOW.getOrDefault(source.getStatus(), Set.of()).contains(newStatus)) {
            throw new IllegalStateException(String.format("Source status %s not found from %s", newStatus, source.getStatus()));
        }

        log.info("Changing status of source {} to {} at {}", source.getStatus(), newStatus, sessionSourceId);
        source.setStatus(newStatus);
        this.postProcessors.forEach(postProcessor -> postProcessor.postProcess(source));
    }
}
