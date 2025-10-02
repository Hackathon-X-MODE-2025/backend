package dev.zendal.etlsetup.service.source.status.processor;

import dev.zendal.etlsetup.domain.source.EtlSessionSourceEntity;
import dev.zendal.etlsetup.domain.source.EtlSessionSourceStatus;
import dev.zendal.etlsetup.domain.EtlSessionStatus;
import dev.zendal.etlsetup.service.session.picker.EtlSessionPickerService;
import dev.zendal.etlsetup.service.session.status.EtlSessionStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class FinishEtlSessionSourceStatusPostProcessor implements EtlSessionSourceStatusPostProcessor {

    private final EtlSessionPickerService etlSessionPickerService;

    private final EtlSessionStatusService etlSessionStatusService;

    @Override
    public void postProcess(EtlSessionSourceEntity entity) {
        final var session = this.etlSessionPickerService.pickById(entity.getEtlSessionId());

        final var statuses = session.getSources().stream().map(EtlSessionSourceEntity::getStatus).collect(Collectors.toSet());

        if (!EtlSessionSourceStatus.terminated().containsAll(statuses)) {
            return;
        }
        log.info("Finished ETL Session Source status processing");

        final var hasError = statuses.contains(EtlSessionSourceStatus.ERROR);
        this.etlSessionStatusService.change(session.getId(), hasError ? EtlSessionStatus.ERROR : EtlSessionStatus.AI_DATABASE_ANALYZING);
    }
}
